package com.jezerm.pokepc.data

import androidx.room.*
import com.jezerm.pokepc.entities.Item

// Position starts from 1
@Entity("tblItem")
@TypeConverters(ItemConverter::class)
data class ItemDto(
    @PrimaryKey
    @ColumnInfo("itemId")
    val item: Item,
    @ColumnInfo("quantity")
    var quantity: Int,
    @ColumnInfo("position")
    var position: Int
)

class ItemConverter {
    @TypeConverter
    fun toItem(value: Int) = Item.values()[value]

    @TypeConverter
    fun fromItem(value: Item) = value.ordinal
}
