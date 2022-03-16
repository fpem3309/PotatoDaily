package com.redoc.potatodaily.db

import androidx.room.*

@Dao
interface BoardDao {

    @Query("SELECT * FROM board ORDER BY date DESC")
    fun getAllBoard() : List<BoardEntity>?

    @Query("SELECT * FROM board WHERE date =:date")
    fun getDayBoard(date: String) : List<BoardEntity>?

    @Insert
    fun insertBoard(board : BoardEntity?)

    @Delete
    fun deleteBoard(board : BoardEntity?)

    @Update
    fun updateBoard(board: BoardEntity?)
}