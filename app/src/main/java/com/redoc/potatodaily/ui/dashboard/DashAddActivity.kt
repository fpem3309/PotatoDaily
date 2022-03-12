package com.redoc.potatodaily.ui.dashboard

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.ActivityDashaddBinding
import com.redoc.potatodaily.db.BoardEntity
import java.lang.Exception
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class DashAddActivity: AppCompatActivity() {

    private lateinit var addBinding: ActivityDashaddBinding
    lateinit var viewModel: DashboardViewModel

    var weather = arrayListOf<String>()
    var people = arrayListOf<String>()
    var school = arrayListOf<String>()
    var couple = arrayListOf<String>()
    var eat = arrayListOf<String>()
    var goods = arrayListOf<String>()

    val REQUEST_CODE = 10
    var uri: Uri? = null

    companion object{const val TAG : String = "로그"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBinding = ActivityDashaddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        viewModel.getAllBoardObservers()

        radioClick()
        checkboxClick()
        imgClick()



        //수정 눌러서 intent로 왔을때
        if (intent.hasExtra("update")) {
            var test = intent.getStringExtra("update")
            Log.d( TAG+"update","$test")
            addBinding.btnSave.text = "Update"

            val test2: ArrayList<String>? = test?.split(",") as ArrayList<String>? //Splitting names

            var upid = test2?.get(0).toString()
            upid = upid.substring(upid.indexOf("=")+1)
            Log.d(TAG+"d", upid)
            addBinding.name.setTag(addBinding.name.id, upid)

        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }


        addBinding.btnSave.setOnClickListener{
            var title = addBinding.name.text.toString()
            var content = addBinding.email.text.toString()
            var mood = addBinding.mdResult.text.toString()

            var weather = weather.toString()
            var people = people.toString()
            var school = school.toString()
            var couple = couple.toString()
            var eat = eat.toString()
            var goods = goods.toString()

            var img = uri.toString()
            val long_now = System.currentTimeMillis()
            var date = Date(long_now)
            Log.d(TAG+"date",date.toString())
            Log.d(TAG, weather.toString())
            if(addBinding.btnSave.text.equals("Save")){
                val board = BoardEntity(0,title,content,mood,weather,people,school,couple,eat,goods,img,date)
                viewModel.insertBoard(board)
                Log.d(TAG,"DashAddActivity - board - insert = $board")
            }else{
                val board = BoardEntity(addBinding.name.getTag(addBinding.name.id).toString().toInt(),title,content,mood,weather,people,school,couple,eat,goods,img,date)
                viewModel.updateBoard(board)
                Log.d(TAG,"DashAddActivity - board - update = $board")
            }
            finish()
        }

    }

    private fun radioClick(){

        addBinding.mdGroup.setOnCheckedChangeListener { group, i ->
            when (i) {
                R.id.md_veryGood -> addBinding.mdResult.text = "very_good"
                R.id.md_good -> addBinding.mdResult.text = "good"
                R.id.md_soso -> addBinding.mdResult.text = "soso"
                R.id.md_bad -> addBinding.mdResult.text = "bad"
                R.id.md_veryBad -> addBinding.mdResult.text = "very_bad"
            }
            Log.d(TAG,""+addBinding.mdResult.text)
        }
    }

    private fun imgClick(){
        addBinding.imgDaily.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_CODE)
            Log.d(TAG+"uri","$uri")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){ uri = data?.data }
        try {
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val img = BitmapFactory.decodeStream(inputStream)
            inputStream!!.close()
            addBinding.imgDaily.setImageBitmap(img)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkboxClick(){

        weather = arrayListOf()
        people = arrayListOf()
        school = arrayListOf()
        couple = arrayListOf()
        eat = arrayListOf()
        goods = arrayListOf()

        var listener = CompoundButton.OnCheckedChangeListener{ bottonView, isChecked ->
            if(isChecked){
                when(bottonView.id){
                    R.id.sunny -> weather.add("sunny")
                    R.id.cloudy -> weather.add("cloudy")
                    R.id.rainy -> weather.add("rainy")
                    R.id.snowy -> weather.add("snowy")
                    R.id.windy -> weather.add("windy")

                    R.id.friend -> people.add("friend")
                    R.id.family -> people.add("family")
                    R.id.coupleFriend -> people.add("coupleFriend")
                    R.id.businessFriend -> people.add("businessFriend")
                    R.id.not -> people.add("not")

                    R.id.classtime -> school.add("classtime")
                    R.id.study -> school.add("study")
                    R.id.assignment -> school.add("assignment")
                    R.id.test -> school.add("test")
                    R.id.team -> school.add("team")

                    R.id.dateCouple -> couple.add("dateCouple")
                    R.id.anniversary -> couple.add("anniversary")
                    R.id.gift -> couple.add("gift")
                    R.id.conflict -> couple.add("conflict")
                    R.id.love -> couple.add("love")

                    R.id.breakfast -> eat.add("breakfast")
                    R.id.lunch -> eat.add("lunch")
                    R.id.dinner -> eat.add("dinner")
                    R.id.midnightSnack -> eat.add("midnightSnack")

                    R.id.alcohol -> goods.add("alcohol")
                    R.id.smoking -> goods.add("smoking")
                    R.id.coffee -> goods.add("coffee")
                    R.id.snack -> goods.add("snack")
                    R.id.drink -> goods.add("drink")
                }
            }
            else{
                when(bottonView.id){
                    R.id.sunny -> weather.remove("sunny")
                    R.id.cloudy -> weather.remove("cloudy")
                    R.id.rainy -> weather.remove("rainy")
                    R.id.snowy -> weather.remove("snowy")
                    R.id.windy -> weather.remove("windy")

                    R.id.friend -> people.remove("friend")
                    R.id.family -> people.remove("family")
                    R.id.coupleFriend -> people.remove("coupleFriend")
                    R.id.businessFriend -> people.remove("businessFriend")
                    R.id.not -> people.remove("not")

                    R.id.classtime -> school.remove("classtime")
                    R.id.study -> school.remove("study")
                    R.id.assignment -> school.remove("assignment")
                    R.id.test -> school.remove("test")
                    R.id.team -> school.remove("team")

                    R.id.dateCouple -> couple.remove("dateCouple")
                    R.id.anniversary -> couple.remove("anniversary")
                    R.id.gift -> couple.remove("gift")
                    R.id.conflict -> couple.remove("conflict")
                    R.id.love -> couple.remove("love")

                    R.id.breakfast -> eat.remove("breakfast")
                    R.id.lunch -> eat.remove("lunch")
                    R.id.dinner -> eat.remove("dinner")
                    R.id.midnightSnack -> eat.remove("midnightSnack")

                    R.id.alcohol -> goods.remove("alcohol")
                    R.id.smoking -> goods.remove("smoking")
                    R.id.coffee -> goods.remove("coffee")
                    R.id.snack -> goods.remove("snack")
                    R.id.drink -> goods.remove("drink")
                }
            }
            Log.d(TAG+"weather", weather.toString())
            Log.d(TAG+"people", people.toString())
            Log.d(TAG+"school", school.toString())
            Log.d(TAG+"couple", couple.toString())
            Log.d(TAG+"eat", eat.toString())
            Log.d(TAG+"goods", goods.toString())
        }
        addBinding.sunny.setOnCheckedChangeListener(listener)
        addBinding.cloudy.setOnCheckedChangeListener(listener)
        addBinding.rainy.setOnCheckedChangeListener(listener)
        addBinding.snowy.setOnCheckedChangeListener(listener)
        addBinding.windy.setOnCheckedChangeListener(listener)

        addBinding.friend.setOnCheckedChangeListener(listener)
        addBinding.family.setOnCheckedChangeListener(listener)
        addBinding.coupleFriend.setOnCheckedChangeListener(listener)
        addBinding.businessFriend.setOnCheckedChangeListener(listener)
        addBinding.not.setOnCheckedChangeListener(listener)

        addBinding.classtime.setOnCheckedChangeListener(listener)
        addBinding.study.setOnCheckedChangeListener(listener)
        addBinding.assignment.setOnCheckedChangeListener(listener)
        addBinding.test.setOnCheckedChangeListener(listener)
        addBinding.team.setOnCheckedChangeListener(listener)

        addBinding.dateCouple.setOnCheckedChangeListener(listener)
        addBinding.anniversary.setOnCheckedChangeListener(listener)
        addBinding.gift.setOnCheckedChangeListener(listener)
        addBinding.conflict.setOnCheckedChangeListener(listener)
        addBinding.love.setOnCheckedChangeListener(listener)

        addBinding.breakfast.setOnCheckedChangeListener(listener)
        addBinding.lunch.setOnCheckedChangeListener(listener)
        addBinding.dinner.setOnCheckedChangeListener(listener)
        addBinding.midnightSnack.setOnCheckedChangeListener(listener)

        addBinding.alcohol.setOnCheckedChangeListener(listener)
        addBinding.smoking.setOnCheckedChangeListener(listener)
        addBinding.coffee.setOnCheckedChangeListener(listener)
        addBinding.snack.setOnCheckedChangeListener(listener)
        addBinding.drink.setOnCheckedChangeListener(listener)

    }

}