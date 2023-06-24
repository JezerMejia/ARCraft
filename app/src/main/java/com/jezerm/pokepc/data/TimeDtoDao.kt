package com.jezerm.pokepc.data

import androidx.room.*
import com.jezerm.pokepc.entities.Inventory
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDtoDao {
    @Query("SELECT * FROM tblTime LIMIT 1")
    fun getTimeDto(): TimeDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimeDto(timeDto: TimeDto)

    @Update
    fun updateTimeDto(timeDto: TimeDto)
}