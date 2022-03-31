package com.redoc.potatodaily.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.redoc.potatodaily.R


class CurrentDayDecorator(context: Context?, currentDay: CalendarDay, moodlist: String) : DayViewDecorator {

    private val vgDrawable: Drawable = context?.getDrawable(R.drawable.very_good)!!
    private val gDrawable: Drawable = context?.getDrawable(R.drawable.good)!!
    private val soDrawable: Drawable = context?.getDrawable(R.drawable.soso)!!
    private val bDrawable: Drawable = context?.getDrawable(R.drawable.bad)!!
    private val vbDrawable: Drawable = context?.getDrawable(R.drawable.very_bad)!!
    private val noDrawable: Drawable = context?.getDrawable(R.drawable.very_good_g)!!

    private var myDay = currentDay
    private var mood = moodlist


    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade?) {
        //Log.d("mood로그", mood)

        if(mood == "very_good"){
            view?.setBackgroundDrawable(vgDrawable)
        }else if(mood == "good"){
            view?.setBackgroundDrawable(gDrawable)
        }else if(mood == "soso"){
            view?.setBackgroundDrawable(soDrawable)
        }else if(mood == "bad"){
            view?.setBackgroundDrawable(bDrawable)
        }else if(mood == "very_bad"){
            view?.setBackgroundDrawable(vbDrawable)
        }else{
            view?.setBackgroundDrawable(noDrawable)
        }


    }
}
