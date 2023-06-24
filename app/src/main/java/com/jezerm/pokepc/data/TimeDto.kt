package com.jezerm.pokepc.data

import androidx.room.*
import java.util.Date

@Entity("tblTime")
@TypeConverters(TimeConverter::class)
data class TimeDto(
    @PrimaryKey
    @ColumnInfo
    val timeId: Int,
    @ColumnInfo("timeStart")
    var timeStart: Date?,
    @ColumnInfo("TimeEnd")
    var timeEnd: Date?
)


class TimeConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
