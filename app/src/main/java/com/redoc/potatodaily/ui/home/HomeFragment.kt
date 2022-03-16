package com.redoc.potatodaily.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.redoc.potatodaily.databinding.FragmentHomeBinding
import com.redoc.potatodaily.ui.dashboard.DashAddActivity

import com.redoc.potatodaily.ui.dashboard.DashboardViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        viewModel.getAllBoardObservers()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


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


            var resultDate = "$year-0$month-$day"
            Log.d("로그 resultDate",resultDate)

            //viewModel.getDayBoard(resultDate)

            var intent = Intent(context, DashAddActivity::class.java)
            intent.apply { this.putExtra("goboard", resultDate) }
            startActivity(intent)

        }




        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}