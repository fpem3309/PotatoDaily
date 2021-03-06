package com.redoc.potatodaily.db

import androidx.room.*
import com.google.gson.Gson
import java.util.*

@Entity(tableName = "board")
data class BoardEntity(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "mood") val mood: String,
    @ColumnInfo(name = "weather") val weather: String,
    @ColumnInfo(name = "people") val people: String,
    @ColumnInfo(name = "school") val school: String,
    @ColumnInfo(name = "couple") val couple: String,
    @ColumnInfo(name = "eat") val eat: String,
    @ColumnInfo(name = "goods") val goods: String,
    @ColumnInfo(name = "img") val img: String,


)

class Converters{

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}