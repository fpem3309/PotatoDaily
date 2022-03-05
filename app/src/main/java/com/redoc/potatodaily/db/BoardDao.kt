package com.redoc.potatodaily.db

import androidx.room.*

@Dao
interface BoardDao {

    @Query("SELECT * FROM board ORDER BY id DESC")
    fun getAllBoard() : List<BoardEntity>?

    @Insert
    fun insertBoard(board : BoardEntity?)

    @Delete
    fun deleteBoard(board : BoardEntity?)

    @Update
    fun updateBoard(board: BoardEntity?)
}