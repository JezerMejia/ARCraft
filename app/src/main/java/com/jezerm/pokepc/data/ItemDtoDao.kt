package com.jezerm.pokepc.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDtoDao {
    @Query("SELECT * FROM tblItem")
    fun getAll(): Flow<List<ItemDto>>

    @Insert
    fun insertItemDto(itemDto: ItemDto)

    @Update
    fun updateItemDto(itemDto: ItemDto)

    @Delete
    fun deleteItemDto(itemDto: ItemDto)
}