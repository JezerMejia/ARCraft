package com.jezerm.pokepc.data

import androidx.room.*
import java.util.Date

@Entity("tblTime")
@TypeConverters(TimeConverters::class)
data class TimeDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val timeId: Int,
    @ColumnInfo("timeStart")
    var timeStart: Date?,
    @ColumnInfo("TimeEnd")
    var timeEnd: Date?
)


class TimeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}