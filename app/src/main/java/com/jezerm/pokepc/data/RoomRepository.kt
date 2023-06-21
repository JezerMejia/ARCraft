package com.jezerm.pokepc.data

import androidx.annotation.WorkerThread
import com.jezerm.pokepc.entities.Inventory
import kotlinx.coroutines.*

class RoomRepository private constructor(private val dao: ItemDtoDao) {
    fun getInventory(inventory: Inventory) = dao.getInventory(inventory)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(itemDto: ItemDto) {
        dao.insertItemDto(itemDto)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(itemDto: ItemDto) {
        dao.updateItemDto(itemDto)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(itemDto: ItemDto) {
        dao.deleteItemDto(itemDto)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clear() {
        dao.deleteAll()
    }

    companion object {
        private var INSTANCE: RoomRepository? = null

        fun init(dao: ItemDtoDao): RoomRepository {
            if (INSTANCE != null) return INSTANCE!!
            INSTANCE = RoomRepository(dao)
            return INSTANCE!!
        }

        fun getInstance(): RoomRepository {
            return INSTANCE!!
        }

        fun close() {
            INSTANCE = null
        }
    }
}
