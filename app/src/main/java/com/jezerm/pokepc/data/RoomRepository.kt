package com.jezerm.pokepc.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class RoomRepository private constructor(private val dao: ItemDtoDao) {

    val itemsDto: Flow<List<ItemDto>> = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(itemDto: ItemDto) {
        dao.insertItemDto(itemDto)
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
    }
}
