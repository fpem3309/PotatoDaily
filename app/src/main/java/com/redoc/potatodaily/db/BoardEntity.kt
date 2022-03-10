package com.redoc.potatodaily.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "img") val img: String


)