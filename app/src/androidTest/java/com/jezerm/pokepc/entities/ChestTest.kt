package com.jezerm.pokepc.entities

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.data.ItemDtoRoomDatabase
import com.jezerm.pokepc.data.RoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ChestTest {
    private val chestOne = Chest(Chest.ChestType.ONE)
    private val chestTwo = Chest(Chest.ChestType.TWO)
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
            chestOne.initFromDatabase()
            chestTwo.initFromDatabase()
            assertEquals(0, chestOne.items.size)
            assertEquals(0, chestTwo.items.size)

            val repository = RoomRepository.getInstance()
            repository.insert(ItemDto(Item.WOOD, 1, 1, chestOne.getId()))
            repository.insert(ItemDto(Item.SUGAR, 6, 1, chestTwo.getId()))
            repository.insert(ItemDto(Item.WOOD, 4, 2, chestTwo.getId()))
            // This should replace the previous value
            repository.insert(ItemDto(Item.WOOD, 5, 2, chestTwo.getId()))

            chestOne.initFromDatabase()
            chestTwo.initFromDatabase()

            val expectedChestOne = arrayOf(
                ItemDto(Item.WOOD, 1, 1, chestOne.getId())
            )
            val expectedChestTwo = arrayOf(
                ItemDto(Item.SUGAR, 6, 1, chestTwo.getId()),
                ItemDto(Item.WOOD, 5, 2, chestTwo.getId())
            )
            assertArrayEquals(
                expectedChestOne,
                chestOne.items.toArray()
            )
            assertArrayEquals(
                expectedChestTwo,
                chestTwo.items.toArray()
            )
        }.join()
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    @Test
    fun saveToDatabase() = runTest(UnconfinedTestDispatcher()) {
        GlobalScope.launch(Dispatchers.IO) {
            chestOne.addItem(Item.GLASS)
            chestTwo.addItem(Item.WOOD, 4)
            chestTwo.addItem(Item.WOOD, 2)
            assertEquals(1, chestOne.items.size)
            assertEquals(1, chestTwo.items.size)

            chestOne.saveToDatabase()
            chestTwo.saveToDatabase()

            chestOne.initFromDatabase()
            chestTwo.initFromDatabase()

            val expectedChestOne = arrayOf(
                ItemDto(Item.GLASS, 1, 1, chestOne.getId())
            )
            val expectedChestTwo = arrayOf(
                ItemDto(Item.WOOD, 6, 1, chestTwo.getId()),
            )
            assertArrayEquals(
                expectedChestOne,
                chestOne.items.toArray()
            )
            assertArrayEquals(
                expectedChestTwo,
                chestTwo.items.toArray()
            )
        }.join()
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    @Test
    fun undoChanges() = runTest(UnconfinedTestDispatcher()) {
        GlobalScope.launch(Dispatchers.IO) {
            val inventory = Inventory()
            chestOne.addItem(Item.GLASS, 4)
            chestOne.addItem(Item.WOOD, 2)

            chestOne.saveToDatabase()
            chestOne.moveAllItemsToInventory(inventory)

            assertEquals(0, chestOne.items.size)
            assertEquals(2, inventory.items.size)

            chestOne.initFromDatabase()
            inventory.initFromDatabase()

            assertEquals(0, inventory.items.size)
            assertEquals(2, chestOne.items.size)
        }.join()
    }
}