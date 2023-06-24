package com.jezerm.pokepc.data

import androidx.annotation.WorkerThread
import com.jezerm.pokepc.entities.Inventory
import kotlinx.coroutines.*
import java.util.Date

class RoomRepository private constructor(private val dao: ItemDtoDao, private val timeDao: TimeDtoDao) {
    fun getInventory(inventory: Inventory) = dao.getInventory(inventory)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getTimer(): TimeDto {
        return timeDao.getTimeDto()
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun startTimer() {
        val currentDate = Date()
        val time = TimeDto(0, currentDate, null)
        timeDao.insertTimeDto(time)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun endTimer() {
        val time = timeDao.getTimeDto()
        time.timeEnd = Date()
        timeDao.updateTimeDto(time)
    }
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

        fun init(dao: ItemDtoDao, timeDao: TimeDtoDao): RoomRepository {
            if (INSTANCE != null) return INSTANCE!!
            INSTANCE = RoomRepository(dao, timeDao)
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
