package com.jezerm.pokepc.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class RoomRepository private constructor(private val dao: ItemDtoDao) {
    val itemsDto: Flow<List<ItemDto>> = dao.getAll()

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
