package com.jezerm.pokepc.data

import androidx.room.*
import com.jezerm.pokepc.entities.Item

// Position starts from 1
@Entity("tblItem", primaryKeys = ["itemId", "inventoryId"])
@TypeConverters(ItemConverter::class)
data class ItemDto(
    @ColumnInfo("itemId")
    val item: Item,
    @ColumnInfo("quantity")
    var quantity: Int,
    @ColumnInfo("position")
    var position: Int,
    @ColumnInfo("inventoryId")
    var inventoryId: Int
)

class ItemConverter {
    @TypeConverter
    fun toItem(value: Int) = Item.values()[value]

    @TypeConverter
    fun fromItem(value: Item) = value.ordinal
}
