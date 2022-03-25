package com.redoc.potatodaily.ui.notifications

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.FragmentMealBinding
import com.redoc.potatodaily.databinding.FragmentWeatherBinding
import com.redoc.potatodaily.ui.dashboard.DashboardViewModel

class WeatherFragment : Fragment() {
    lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sunnyCnt = viewModel.getWeatherBoard("sunny")
        val cludyCnt = viewModel.getWeatherBoard("cloudy")
        val rainyCnt = viewModel.getWeatherBoard("rainy")
        val snowyCnt = viewModel.getWeatherBoard("snowy")
        val windyCnt = viewModel.getWeatherBoard("windy")


        var chart = binding.barChart

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1.0f,sunnyCnt!!.toFloat()))
        entries.add(BarEntry(2.0f,cludyCnt!!.toFloat()))
        entries.add(BarEntry(3.0f,rainyCnt!!.toFloat()))
        entries.add(BarEntry(4.0f,snowyCnt!!.toFloat()))
        entries.add(BarEntry(5.0f,windyCnt!!.toFloat()))

        var barDataSet = BarDataSet(entries,"날씨")//데이터셋 초기화 하기

        val dataSet :ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(barDataSet)
        val data = BarData(dataSet)
        data.barWidth = 0.2f//막대 너비 설정하기

        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return (value.toInt().toString())
            }
        }

        chart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            setFitBars(true)
            invalidate()
            description.isEnabled = false //차트 옆에 별도로 표기되는 description이다. false로 설정하여 안보이게 했다.
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정

            axisLeft.run {  //Y방향 축
                axisMinimum = 0f // 최소값 0
                granularity = 50f // 50 단위마다 선을 그리려고 granularity 설정 해 주었다.
            }
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM//X축을 아래에다가 둔다.
                granularity = 1.0f //  단위만큼 간격 두기
                setDrawGridLines(false) // 격자
                valueFormatter = MyXAxisFormatter() // 축 라벨 값 바꿔주기 위함
            }
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            axisLeft.isEnabled = false
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
        }



        return root
    }

    inner class MyXAxisFormatter : ValueFormatter(){
        private val days = arrayOf("맑음","구름","비","눈","바람")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()-1) ?: value.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}