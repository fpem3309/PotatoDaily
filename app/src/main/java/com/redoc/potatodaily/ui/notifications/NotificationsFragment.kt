package com.redoc.potatodaily.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root



        var chart = binding.pieChart
        chart.setUsePercentValues(true)

        val test = ArrayList<PieEntry>()
        test.add(PieEntry(1.2f, ResourcesCompat.getDrawable(resources,R.drawable.very_goodchart,null)))
        test.add(PieEntry(1.2f, ResourcesCompat.getDrawable(resources,R.drawable.goodchart,null)))
        test.add(PieEntry(1.2f, ResourcesCompat.getDrawable(resources,R.drawable.sosochart,null)))
        test.add(PieEntry(1.2f, ResourcesCompat.getDrawable(resources,R.drawable.badchart,null)))
        test.add(PieEntry(1.2f, ResourcesCompat.getDrawable(resources,R.drawable.very_badchart,null)))



//        test.add(PieEntry(1.2f, ResourcesCompat.getDrawable(resources,R.drawable.very_good,null)))
//        test.add(PieEntry(1.2f,ResourcesCompat.getDrawable(resources,R.drawable.good,null)))
//        test.add(PieEntry(1.2f,ResourcesCompat.getDrawable(resources,R.drawable.soso,null)))
//        test.add(PieEntry(1.2f,ResourcesCompat.getDrawable(resources,R.drawable.bad,null)))
//        test.add(PieEntry(1.2f,ResourcesCompat.getDrawable(resources,R.drawable.very_bad,null)))

        val colorsItems = ArrayList<Int>()
        for( c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for( c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for( c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for( c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(test,"")
        pieDataSet.setDrawIcons(true)
        pieDataSet.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 0f
        }

        val pieData = PieData(pieDataSet)
        chart.apply {
            data = pieData
            description.isEnabled = false
            centerText = "기분 분포"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}