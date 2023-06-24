package com.jezerm.pokepc.entities

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.data.ItemDtoRoomDatabase
import com.jezerm.pokepc.data.RoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class InventoryTest {
    private val inventory = Inventory()
    private lateinit var db: ItemDtoRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ItemDtoRoomDatabase::class.java
        ).build()

        RoomRepository.init(db.itemDtoDao())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        RoomRepository.close()
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    @Test
    fun initFromDatabase() = runTest(UnconfinedTestDispatcher()) {
        GlobalScope.launch(Dispatchers.IO) {
            inventory.initFromDatabase()
            assertEquals(0, inventory.items.size)

            val repository = RoomRepository.getInstance()
            repository.insert(ItemDto(Item.OAK_PLANKS, 1, 1, inventory.getId()))

            inventory.initFromDatabase()
            assertEquals(1, inventory.items.size)
        }.join()
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    @Test
    fun saveToDatabase() = runTest(UnconfinedTestDispatcher()) {
        GlobalScope.launch(Dispatchers.IO) {
            inventory.addItem(Item.GLASS)
            inventory.addItem(Item.OAK_PLANKS, 4)
            assertEquals(2, inventory.items.size)

            inventory.saveToDatabase()
            inventory.initFromDatabase()
            assertEquals(2, inventory.items.size)
            assert(inventory.items.any { v -> v.item == Item.GLASS })
            assert(inventory.items.any { v -> v.item == Item.OAK_PLANKS && v.quantity == 4 })
        }.join()
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    @Test
    fun undoChanges() = runTest(UnconfinedTestDispatcher()) {
        GlobalScope.launch(Dispatchers.IO) {
            val craftingTable = CraftingTable()
            val position = inventory.addItem(Item.SUGAR, 4)
            craftingTable.moveItemFromInventory(inventory, position)
        }.join()
    }
}