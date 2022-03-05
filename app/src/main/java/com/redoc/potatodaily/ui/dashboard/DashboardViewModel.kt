package com.redoc.potatodaily.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redoc.potatodaily.db.BoardEntity
import com.redoc.potatodaily.db.RoomAppDB

class DashboardViewModel(app:Application) : AndroidViewModel(app) {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is dashboard Fragment"
//    }
//    val text: LiveData<String> = _text

    lateinit var allBoards : MutableLiveData<List<BoardEntity>>

    init {
        allBoards = MutableLiveData()
    }

    fun getAllBoardObservers(): MutableLiveData<List<BoardEntity>> {
        return allBoards
    }

    fun getAllBoard() {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val list = boardDao?.getAllBoard()

        allBoards.postValue(list!!)
    }

    fun insertBoard(entity: BoardEntity){
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        boardDao?.insertBoard(entity)
        getAllBoard()
    }

    fun deleteBoard(entity: BoardEntity){
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        boardDao?.deleteBoard(entity)
        getAllBoard()
    }

    fun updateBoard(entity: BoardEntity){
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        boardDao?.updateBoard(entity)
        getAllBoard()
    }
}