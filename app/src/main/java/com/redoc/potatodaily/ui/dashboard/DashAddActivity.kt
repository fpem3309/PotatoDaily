package com.redoc.potatodaily.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.redoc.potatodaily.databinding.ActivityDashaddBinding
import com.redoc.potatodaily.db.BoardEntity

class DashAddActivity: AppCompatActivity() {

    private lateinit var addBinding: ActivityDashaddBinding
    lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBinding = ActivityDashaddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        addBinding.btnSave.setOnClickListener{
            var title = addBinding.name.text.toString()
            var content = addBinding.email.text.toString()

            if(addBinding.btnSave.text.equals("Save")){
                val board = BoardEntity(0,title,content)
                viewModel.insertBoard(board)
            }
            addBinding.name.setText("")
            addBinding.email.setText("")
            finish()
        }
    }
}