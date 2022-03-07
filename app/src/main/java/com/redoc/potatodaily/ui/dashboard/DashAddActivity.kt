package com.redoc.potatodaily.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.ActivityDashaddBinding
import com.redoc.potatodaily.db.BoardEntity

class DashAddActivity: AppCompatActivity() {

    private lateinit var addBinding: ActivityDashaddBinding
    lateinit var viewModel: DashboardViewModel
    var result = arrayListOf<String>()

    companion object{const val TAG : String = "로그"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBinding = ActivityDashaddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)


        radioClick()
        checkboxClick()


        addBinding.btnSave.setOnClickListener{
            var title = addBinding.name.text.toString()
            var content = addBinding.email.text.toString()
            var mood = addBinding.mdResult.text.toString()
            var weather = result.toString()
            Log.d(TAG, result.toString())
            if(addBinding.btnSave.text.equals("Save")){
                val board = BoardEntity(0,title,content,mood,weather)
                viewModel.insertBoard(board)
                Log.d(TAG,"DashAddActivity - board = $board")
            }
            addBinding.name.setText("")
            addBinding.email.setText("")
            finish()
        }
    }

    private fun radioClick(){

        addBinding.mdGroup.setOnCheckedChangeListener { group, i ->
            when (i) {
                R.id.md_veryGood -> addBinding.mdResult.text = "very good"
                R.id.md_good -> addBinding.mdResult.text = "good"
                R.id.md_soso -> addBinding.mdResult.text = "soso"
                R.id.md_bad -> addBinding.mdResult.text = "bad"
                R.id.md_veryBad -> addBinding.mdResult.text = "very bad"
            }
            Log.d(TAG,""+addBinding.mdResult.text)
        }
    }

    private fun checkboxClick(){

        result = arrayListOf()

        var listener = CompoundButton.OnCheckedChangeListener{ bottonView, isChecked ->
            if(isChecked){
                when(bottonView.id){
                    R.id.sunny -> result.add(addBinding.sunny.text as String)
                    R.id.cloudy -> result.add(addBinding.cloudy.text  as String)
                    R.id.rainy -> result.add(addBinding.rainy.text  as String)
                    R.id.snowy -> result.add(addBinding.snowy.text  as String)
                    R.id.windy -> result.add(addBinding.windy.text  as String)

                    R.id.friend -> Log.d(TAG, "friend")
                    R.id.family -> Log.d(TAG, "family")
                    R.id.coupleFriend -> Log.d(TAG, "coupleFriend")
                    R.id.businessFriend -> Log.d(TAG, "businessFriend")
                    R.id.not -> Log.d(TAG, "not")

                    R.id.classtime -> Log.d(TAG, "classtime")
                    R.id.study -> Log.d(TAG, "study")
                    R.id.assignment -> Log.d(TAG, "assignment")
                    R.id.test -> Log.d(TAG, "test")
                    R.id.team -> Log.d(TAG, "team")

                    R.id.dateCouple -> Log.d(TAG, "dateCouple")
                    R.id.anniversary -> Log.d(TAG, "anniversary")
                    R.id.gift -> Log.d(TAG, "gift")
                    R.id.conflict -> Log.d(TAG, "conflict")
                    R.id.love -> Log.d(TAG, "love")

                    R.id.breakfast -> Log.d(TAG, "breakfast")
                    R.id.lunch -> Log.d(TAG, "lunch")
                    R.id.dinner -> Log.d(TAG, "dinner")
                    R.id.midnightSnack -> Log.d(TAG, "midnightSnack")

                    R.id.alcohol -> Log.d(TAG, "alcohol")
                    R.id.smoking -> Log.d(TAG, "smoking")
                    R.id.coffee -> Log.d(TAG, "coffee")
                    R.id.snack -> Log.d(TAG, "snack")
                    R.id.drink -> Log.d(TAG, "drink")
                }
            }
            else{
                when(bottonView.id){
                    R.id.sunny -> result.remove(addBinding.sunny.text as String)
                    R.id.cloudy -> result.remove(addBinding.cloudy.text  as String)
                    R.id.rainy -> result.remove(addBinding.rainy.text  as String)
                    R.id.snowy -> result.remove(addBinding.snowy.text  as String)
                    R.id.windy -> result.remove(addBinding.windy.text  as String)
                }
            }
//            Log.d(TAG, result.distinct().toString())
            Log.d(TAG, result.toString())
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