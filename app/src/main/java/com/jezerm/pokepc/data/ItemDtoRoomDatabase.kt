package com.jezerm.pokepc.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ItemDto::class), version = 1, exportSchema = false)
abstract class ItemDtoRoomDatabase : RoomDatabase() {

    abstract fun itemDtoDao(): ItemDtoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ItemDtoRoomDatabase? = null

        fun getDatabase(context: Context): ItemDtoRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDtoRoomDatabase::class.java,
                    "RoomTest"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}