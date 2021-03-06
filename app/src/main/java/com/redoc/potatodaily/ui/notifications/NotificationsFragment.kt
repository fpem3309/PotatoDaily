package com.redoc.potatodaily.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.FragmentNotificationsBinding
import com.redoc.potatodaily.ui.dashboard.DashboardViewModel

class NotificationsFragment : Fragment() {

    lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val cntVeryGood = viewModel.getMoodBoard("very_good")
        val cntGood = viewModel.getMoodBoard("good")
        val cntSoso = viewModel.getMoodBoard("soso")
        val cntBad = viewModel.getMoodBoard("bad")
        val cntVeryBad = viewModel.getMoodBoard("very_bad")


        val AllCnt = cntVeryGood!! + cntGood!! + cntSoso!! + cntBad!! + cntVeryBad!!

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val chart = binding.pieChart
        chart.setUsePercentValues(true)

        chart.legend.isEnabled = false // x-Values List false 안보이게

        val pieList = ArrayList<PieEntry>()

        if(cntVeryGood!=0) {
            pieList.add(PieEntry(cntVeryGood / AllCnt.toFloat(), ResourcesCompat.getDrawable(resources, R.drawable.very_goodchart, null)))
        }
        if(cntGood!=0) {
            pieList.add(PieEntry(cntGood / AllCnt.toFloat(), ResourcesCompat.getDrawable(resources, R.drawable.goodchart, null)))
        }
        if(cntSoso!=0) {
            pieList.add(PieEntry(cntSoso / AllCnt.toFloat(), ResourcesCompat.getDrawable(resources, R.drawable.sosochart, null)))
        }
        if(cntBad!=0) {
            pieList.add(PieEntry(cntBad / AllCnt.toFloat(), ResourcesCompat.getDrawable(resources, R.drawable.badchart, null)))
        }
        if(cntVeryBad!=0) {
            pieList.add(PieEntry(cntVeryBad / AllCnt.toFloat(), ResourcesCompat.getDrawable(resources, R.drawable.very_badchart, null)))
        }

        val colorsItems = ArrayList<Int>()
        for( c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for( c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for( c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for( c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(pieList,"")
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


        val pagerAdapter = PagerRecyclerAdapter(requireActivity())

        pagerAdapter.addFragment(MealFragment())
        pagerAdapter.addFragment(WeatherFragment())
        pagerAdapter.addFragment(PeopleFragment())
        pagerAdapter.addFragment(SchoolFragment())
        pagerAdapter.addFragment(CoupleFragment())
        pagerAdapter.addFragment(GoodsFragment())

        binding.viewpager.adapter = pagerAdapter

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            // Paging 완료되면 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position) } })

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}