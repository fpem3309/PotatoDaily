package com.redoc.potatodaily.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    // This property is only valid between onCreateView and
    // onDestroyView.
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

            Log.d("로그 widget", widget.toString())
            Log.d("로그 date", date.toString())
            Log.d("로그 selected", selected.toString())

            var all_day = date.toString()

            all_day = all_day.substring(all_day.indexOf("{") + 1, all_day.length - 1)

            val dayArray = all_day.split("-".toRegex())

            var year = dayArray[0]

            var month = dayArray[1].toInt()
            month += 1

            var day = dayArray[2]

            var resultDate = "$year-$month-$day"
            Log.d("로그 resultDate",resultDate)

            var intent = Intent(context, DashAddActivity::class.java)
            intent.apply { this.putExtra("goboard", resultDate) }
            startActivity(intent)

        }
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllBoardObservers().observe(this, androidx.lifecycle.Observer {

            val calList = ArrayList<CalendarDay>()
            var moodlist = ArrayList<String>()

            val board = it
            Log.d("로그board", board.toString())

            for(i: Int in 0..board.size-1){
                board[i].date
                calList.add(CalendarDay.from(java.sql.Date.valueOf(board[i].date)))
                moodlist.add(board[i].mood)
            }
            for (calDay in calList){
                binding.calendarView.addDecorators(CurrentDayDecorator(context,calDay,moodlist[calList.indexOf(calDay)]))
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}