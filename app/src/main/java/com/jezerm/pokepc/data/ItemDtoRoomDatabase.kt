package com.jezerm.pokepc.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = arrayOf(ItemDto::class, TimeDto::class), version = 1, exportSchema = false)
abstract class ItemDtoRoomDatabase : RoomDatabase() {

    abstract fun itemDtoDao(): ItemDtoDao
    abstract fun timeDtoDao(): TimeDtoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ItemDtoRoomDatabase? = null

        fun getDatabase(context: Context): ItemDtoRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database

            //context.deleteDatabase("RoomTest")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDtoRoomDatabase::class.java,
                    "RoomTest"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { roomDb ->
                                    val dao = roomDb.itemDtoDao()
                                    chestOnePopulate.forEach { item -> dao.insertItemDto(item) }
                                    chestTwoPopulate.forEach { item -> dao.insertItemDto(item) }
                                    chestThreePopulate.forEach { item -> dao.insertItemDto(item) }
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}