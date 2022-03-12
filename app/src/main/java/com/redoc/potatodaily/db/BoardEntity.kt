package com.redoc.potatodaily.db

import androidx.room.*
import com.google.gson.Gson
import java.util.*

@Entity(tableName = "board")
data class BoardEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "mood") val mood: String,
    @ColumnInfo(name = "weather") val weather: String,
    @ColumnInfo(name = "people") val people: String,
    @ColumnInfo(name = "school") val school: String,
    @ColumnInfo(name = "couple") val couple: String,
    @ColumnInfo(name = "eat") val eat: String,
    @ColumnInfo(name = "goods") val goods: String,
    @ColumnInfo(name = "img") val img: String,
    @ColumnInfo(name = "date") val date: Date


)

@ProvidedTypeConverter
class Converters(private val gson: Gson){

    @TypeConverter
    fun listToJson(value: Date): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): Date {
        return gson.fromJson(value, Date::class.java )
    }

}