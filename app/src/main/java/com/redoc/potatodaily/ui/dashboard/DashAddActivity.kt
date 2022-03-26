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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class DashAddActivity : AppCompatActivity() {

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

    companion object {
        const val TAG: String = "로그"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBinding = ActivityDashaddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        viewModel.getAllBoardObservers()

        val long_now = System.currentTimeMillis()   // 작성한 시간 가져오기
        val date_now = Date(long_now)   // Date 타입으로 변환
        val date_format = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))  // 날짜 가져올 형태
        val date = date_format.format(date_now)

        radioClick()
        checkboxClick()
        imgClick()


        if (intent.hasExtra("update")) {    // 수정 눌렀을때

            val uData = intent.getStringExtra("update")
            Log.d(TAG + "update", "$uData")
            addBinding.btnSave.text = "Update"


            val getdata = viewModel.getDayBoard(uData!!)

            updateCheck(getdata!!)


        } else if (intent.hasExtra("goboard")) {   // calendar에서 날짜 선택했을때

            val getDate = intent.getStringExtra("goboard")

            addBinding.writeDate.text = getDate
            addBinding.writeDate.setTag(addBinding.writeDate.id, getDate)


            // 해당 날짜가 있으면 Update 없으면 Save
            if (viewModel.getDayBoard(getDate!!).toString() != "[]") {
                addBinding.btnSave.text = "Update"
                val getdata = viewModel.getDayBoard(getDate)
                updateCheck(getdata!!)
            } else {
                addBinding.btnSave.text = "Save"
            }


        }

        addBinding.btnSave.setOnClickListener {

            val setDate = addBinding.writeDate.text.toString()
            val title = addBinding.name.text.toString()
            val mood = addBinding.mdResult.text.toString()

            val weather = weather.toString()
            val people = people.toString()
            val school = school.toString()
            val couple = couple.toString()
            val eat = eat.toString()
            val goods = goods.toString()
            val img = uri.toString()

            Log.d(TAG + "date", date.toString())

            if (addBinding.btnSave.text.equals("Save")) {
                val board = BoardEntity(
                    setDate,
                    title,
                    mood,
                    weather,
                    people,
                    school,
                    couple,
                    eat,
                    goods,
                    img
                )
                viewModel.insertBoard(board)
                Log.d(TAG, "DashAddActivity - board - insert = $board")
            } else {  // Update 일때
                val board = BoardEntity(
                    setDate,
                    title,
                    mood,
                    weather,
                    people,
                    school,
                    couple,
                    eat,
                    goods,
                    img
                )
                viewModel.updateBoard(board)
                Log.d(
                    TAG,
                    "DashAddActivity - board - update = $board" + addBinding.name.getTag(addBinding.name.id)
                        .toString()
                )
            }
            finish()
        }


    }

    private fun updateCheck(getdata: List<BoardEntity>) {

        val getDate = getdata[0].date
        val getTitle = getdata[0].title
        val getMood = getdata[0].mood
        val getWeather = getdata[0].weather
        val getPeople = getdata[0].people
        val getSchool = getdata[0].school
        val getCouple = getdata[0].couple
        val getEat = getdata[0].eat
        val getGoods = getdata[0].goods
        val getImg = getdata[0].img

        addBinding.writeDate.text = getDate
        addBinding.name.setText(getTitle)

        when (getMood) {
            "very_good" -> addBinding.mdVeryGood.isChecked = true
            "good" -> addBinding.mdGood.isChecked = true
            "soso" -> addBinding.mdSoso.isChecked = true
            "bad" -> addBinding.mdBad.isChecked = true
            "very_bad" -> addBinding.mdVeryBad.isChecked = true
        }
        if (getWeather.contains("sunny")) addBinding.sunny.isChecked = true
        if (getWeather.contains("cloudy")) addBinding.cloudy.isChecked = true
        if (getWeather.contains("rainy")) addBinding.rainy.isChecked = true
        if (getWeather.contains("snowy")) addBinding.snowy.isChecked = true
        if (getWeather.contains("windy")) addBinding.windy.isChecked = true

        if (getPeople.contains("friend")) addBinding.friend.isChecked = true
        if (getPeople.contains("family")) addBinding.family.isChecked = true
        if (getPeople.contains("coupleFriend")) addBinding.coupleFriend.isChecked = true
        if (getPeople.contains("businessFriend")) addBinding.businessFriend.isChecked = true
        if (getPeople.contains("not")) addBinding.not.isChecked = true

        if (getSchool.contains("classtime")) addBinding.classtime.isChecked = true
        if (getSchool.contains("study")) addBinding.study.isChecked = true
        if (getSchool.contains("assignment")) addBinding.assignment.isChecked = true
        if (getSchool.contains("test")) addBinding.test.isChecked = true
        if (getSchool.contains("team")) addBinding.team.isChecked = true

        if (getCouple.contains("dateCouple")) addBinding.dateCouple.isChecked = true
        if (getCouple.contains("anniversary")) addBinding.anniversary.isChecked = true
        if (getCouple.contains("gift")) addBinding.gift.isChecked = true
        if (getCouple.contains("conflict")) addBinding.conflict.isChecked = true
        if (getCouple.contains("love")) addBinding.love.isChecked = true

        if (getEat.contains("breakfast")) addBinding.breakfast.isChecked = true
        if (getEat.contains("lunch")) addBinding.lunch.isChecked = true
        if (getEat.contains("dinner")) addBinding.dinner.isChecked = true
        if (getEat.contains("midnightSnack")) addBinding.midnightSnack.isChecked = true

        if (getGoods.contains("alcohol")) addBinding.alcohol.isChecked = true
        if (getGoods.contains("smoking")) addBinding.smoking.isChecked = true
        if (getGoods.contains("coffee")) addBinding.coffee.isChecked = true
        if (getGoods.contains("snack")) addBinding.snack.isChecked = true
        if (getGoods.contains("drink")) addBinding.drink.isChecked = true

        Log.d("로그 이미지",getImg)
        if (getImg != "null") addBinding.imgDaily.setImageURI(Uri.parse(getImg))
        uri = (Uri.parse(getImg))

    }

    private fun radioClick() {

        addBinding.mdGroup.setOnCheckedChangeListener { group, i ->
            when (i) {
                R.id.md_veryGood -> addBinding.mdResult.text = "very_good"
                R.id.md_good -> addBinding.mdResult.text = "good"
                R.id.md_soso -> addBinding.mdResult.text = "soso"
                R.id.md_bad -> addBinding.mdResult.text = "bad"
                R.id.md_veryBad -> addBinding.mdResult.text = "very_bad"
            }
            Log.d(TAG, "" + addBinding.mdResult.text)
        }
    }

    private fun imgClick() {
        addBinding.imgDaily.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_CODE)
            Log.d(TAG + "uri", "$uri")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            uri = data?.data
        }
        try {
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val img = BitmapFactory.decodeStream(inputStream)
            inputStream!!.close()
            addBinding.imgDaily.setImageBitmap(img)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkboxClick() {

        weather = arrayListOf()
        people = arrayListOf()
        school = arrayListOf()
        couple = arrayListOf()
        eat = arrayListOf()
        goods = arrayListOf()

        var listener = CompoundButton.OnCheckedChangeListener { bottonView, isChecked ->
            if (isChecked) {
                when (bottonView.id) {
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
            } else {
                when (bottonView.id) {
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
        }
        Log.d(TAG + "weather", weather.toString())
        Log.d(TAG + "people", people.toString())
        Log.d(TAG + "school", school.toString())
        Log.d(TAG + "couple", couple.toString())
        Log.d(TAG + "eat", eat.toString())
        Log.d(TAG + "goods", goods.toString())

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