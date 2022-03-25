package com.redoc.potatodaily.ui.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.redoc.potatodaily.db.BoardEntity
import com.redoc.potatodaily.db.RoomAppDB

// 뷰모델은 데이터의 변경사항을 알려주는 라이브 데이터를 가지고 있고
class DashboardViewModel(app:Application) : AndroidViewModel(app) {

    // 뮤터블 라이브 데이터(수정 가능)
    // 라이브 데이터 (수정 불가능, 읽기 전용)

    lateinit var allBoards : MutableLiveData<List<BoardEntity>>

    companion object{const val TAG : String = "로그"}

    init {
        allBoards = MutableLiveData()
        Log.d(TAG, "DashboardViewModel - 생성자 호출")
    }

    fun getAllBoardObservers(): MutableLiveData<List<BoardEntity>> {
        getAllBoard()
        return allBoards
    }


    fun getMonthBoardObservers(month: String): MutableLiveData<List<BoardEntity>> {
        getMonthBoard(month)
        return allBoards
    }

    fun getMonthBoard(resultDate: String) {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val list = boardDao?.getMonthBoard(resultDate)
        Log.d(TAG+"getMonthBoard2","$list")
        allBoards.postValue(list!!)
    }


    fun getDayBoard(resultDate: String): List<BoardEntity>? {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val list = boardDao?.getDayBoard(resultDate)
        Log.d(TAG+"getDayBoard","$list")
        return list
    }

    private fun getAllBoard() {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val list = boardDao?.getAllBoard()
        Log.d(TAG,"$list")
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
        //getAllBoard()
        getMonthBoard(entity.date.substring(5,6))
    }

    fun updateBoard(entity: BoardEntity){
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        boardDao?.updateBoard(entity)
        getAllBoard()
    }

    //Piechart
    fun getMoodBoard(mood: String): Int? {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val cnt = boardDao?.getMoodBoard(mood)
        Log.d(TAG+"getMoodBoard","$cnt")
        return cnt
    }
    //Barchart
    fun getMealBoard(eat: String): Int? {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val cnt = boardDao?.getMealBoard(eat)
        Log.d(TAG+"getMoodBoard","$cnt")
        return cnt
    }

    fun getWeatherBoard(weather: String): Int? {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val cnt = boardDao?.getWeatherBoard(weather)
        return cnt
    }

    fun getPeopleBoard(people: String): Int? {
        val boardDao = RoomAppDB.getAppDatabase((getApplication()))?.BoardDao()
        val cnt = boardDao?.getPeopleBoard(people)
        return cnt
    }

}