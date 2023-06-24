package com.jezerm.pokepc.data

import androidx.room.*
import com.jezerm.pokepc.entities.Inventory
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDtoDao {
    @Query("SELECT * FROM tblItem")
    fun getAll(): Flow<List<ItemDto>>

    @Query("SELECT * FROM tblItem WHERE inventoryId = :inventoryId")
    fun getInventory(inventoryId: Int): Flow<List<ItemDto>>

    fun getInventory(inventory: Inventory): Flow<List<ItemDto>> {
        return getInventory(inventory.getId())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemDto(itemDto: ItemDto)

    @Update
    fun updateItemDto(itemDto: ItemDto)

    @Delete
    fun deleteItemDto(itemDto: ItemDto)

    @Query("DELETE FROM tblItem")
    fun deleteAll()
}