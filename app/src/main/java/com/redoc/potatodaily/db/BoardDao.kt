package com.redoc.potatodaily.db

import androidx.room.*

@Dao
interface BoardDao {

    @Query("SELECT * FROM board ORDER BY date DESC")
    fun getAllBoard() : List<BoardEntity>?

    @Query("SELECT * FROM board WHERE date =:date ORDER BY date DESC")
    fun getDayBoard(date: String) : List<BoardEntity>?

    @Query("SELECT * FROM board WHERE substr(date,6,1) =:month ORDER BY date DESC")
    fun getMonthBoard(month: String) : List<BoardEntity>?

    @Query("SELECT count(*) FROM board WHERE mood =:mood ORDER BY date DESC")
    fun getMoodBoard(mood: String) : Int?

    @Insert
    fun insertBoard(board : BoardEntity?)

    @Delete
    fun deleteBoard(board : BoardEntity?)

    @Update
    fun updateBoard(board: BoardEntity?)
}