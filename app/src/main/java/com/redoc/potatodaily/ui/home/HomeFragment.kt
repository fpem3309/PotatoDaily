package com.redoc.potatodaily.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.redoc.potatodaily.databinding.FragmentHomeBinding
import com.redoc.potatodaily.ui.dashboard.DashAddActivity

import com.redoc.potatodaily.ui.dashboard.DashboardViewModel
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->

            var allDay = date.toString()
            allDay = allDay.substring(allDay.indexOf("{") + 1, allDay.length - 1)

            val dayArray = allDay.split("-".toRegex())
            val year = dayArray[0]
            var month = dayArray[1].toInt()
            month += 1 // 0월 -> 1월

            val day = dayArray[2]
            val resultDate = "$year-$month-$day"

            val intent = Intent(context, DashAddActivity::class.java)
            intent.apply { this.putExtra("goboard", resultDate) }
            startActivity(intent)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllBoardObservers().observe(this, androidx.lifecycle.Observer {

            val calList = ArrayList<CalendarDay>()
            val moodList = ArrayList<String>()

            val board = it

            for(i: Int in board.indices){
                board[i].date
                calList.add(CalendarDay.from(java.sql.Date.valueOf(board[i].date)))
                moodList.add(board[i].mood)
            }
            for (calDay in calList){
                binding.calendarView.addDecorators(CurrentDayDecorator(context,calDay,moodList[calList.indexOf(calDay)]))
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}