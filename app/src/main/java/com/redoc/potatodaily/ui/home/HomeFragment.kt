package com.redoc.potatodaily.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.FragmentHomeBinding
import com.redoc.potatodaily.ui.dashboard.DashAddActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            Log.d("로그 widget", widget.toString())
            Log.d("로그 date", date.toString())
            Log.d("로그 selected", selected.toString())

            var intent =  Intent(context, DashAddActivity::class.java)
            startActivity(intent)

        })


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}