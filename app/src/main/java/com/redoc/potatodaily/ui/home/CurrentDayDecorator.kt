package com.redoc.potatodaily.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.redoc.potatodaily.R


class CurrentDayDecorator(context: Context?, currentDay: CalendarDay) : DayViewDecorator {

    private val drawable: Drawable = context?.getDrawable(R.drawable.very_good)!!
    private var myDay = currentDay

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay

    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}
