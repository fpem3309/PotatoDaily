package com.redoc.potatodaily.ui.dashboard

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.BoardItemBinding
import com.redoc.potatodaily.db.BoardEntity

class RecyclerViewAdapter(val listener: RowClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items = ArrayList<BoardEntity>()

    fun setListData(data: ArrayList<BoardEntity>) {
            this.items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = BoardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // 항상 닫아노기
        binding.expandableLayout.visibility = GONE

        return MyViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])

            // 누를때마다 열고 닫기
            if (holder.expandableLayout.visibility == VISIBLE) holder.expandableLayout.visibility =
                GONE else holder.expandableLayout.visibility = VISIBLE
        }
        holder.bind(items[position])

    }


    class MyViewHolder(private val binding: BoardItemBinding, val listener: RowClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        private val tvTitle = binding.tvTitle
        private val tvWriteDate = binding.tvWriteDate

        private val imgMood = binding.imgMood
        private val imgSunny = binding.imgSunny
        private val imgCloudy = binding.imgCloudy
        private val imgRainy = binding.imgRainy
        private val imgSnowy = binding.imgSnowy
        private val imgWindy = binding.imgWindy

        private val imgFamily = binding.imgFamily
        private val imgCoupleFriend = binding.imgCoupleFriend
        private val imgFriend = binding.imgFriend
        private val imgNot = binding.imgNot
        private val imgBuisnessFriend = binding.imgBuisnessFriend

        private val imgClasstime = binding.imgClasstime
        private val imgStudy = binding.imgStudy
        private val imgAssignment = binding.imgAssignment
        private val imgTest = binding.imgTest
        private val imgTeam = binding.imgTeam

        private val imgDateCouple = binding.imgDateCouple
        private val imgAnniversary = binding.imgAnniversary
        private val imgGift = binding.imgGift
        private val imgConflict = binding.imgConflict
        private val imgLove = binding.imgLove

        private val imgBreakfast = binding.imgBreakfast
        private val imgLunch = binding.imgLunch
        private val imgDinner = binding.imgDinner
        private val imgMidnightSnack = binding.imgMidnightSnack

        private val imgAlcohol = binding.imgAlcohol
        private val imgSmoking = binding.imgSmoking
        private val imgCoffee = binding.imgCoffee
        private val imgSnack = binding.imgSnack
        private val imgDrink = binding.imgDrink
        private val imgDaily = binding.imgDaily

        private val deleteBoardID = binding.deleteBoardID
        private val updateBoardID = binding.updateBoardID
        val expandableLayout = binding.expandableLayout

        fun bind(data: BoardEntity) {

            tvTitle.text = data.title
            tvWriteDate.text = data.date

            if (data.img == "null")imgDaily.visibility = GONE else imgDaily.setImageURI(Uri.parse(data.img))

            if(tvTitle.text.equals("기록이 없어요")){
                deleteBoardID.visibility = GONE
                updateBoardID.visibility = GONE
                itemView.isEnabled = false
            }

            //moood
            if (data.mood == "very_good") {
                imgMood.setImageResource(R.drawable.very_good)
            } else if (data.mood == "good") {
                imgMood.setImageResource(R.drawable.good)
            } else if (data.mood == "soso") {
                imgMood.setImageResource(R.drawable.soso)
            } else if (data.mood == "bad") {
                imgMood.setImageResource(R.drawable.bad)
            } else if (data.mood == "very_bad") {
                imgMood.setImageResource(R.drawable.very_bad)
            } else {
                imgMood.setImageResource(R.drawable.very_good_g)
            }

            //weather
            if (data.weather.contains("sunny")) imgSunny.setImageResource(R.drawable.sunny) else imgSunny.setImageResource(
                R.drawable.sunny_g
            )
            if (data.weather.contains("cloudy")) imgCloudy.setImageResource(R.drawable.cloudy) else imgCloudy.setImageResource(
                R.drawable.cloudy_g
            )
            if (data.weather.contains("rainy")) imgRainy.setImageResource(R.drawable.rainy) else imgRainy.setImageResource(
                R.drawable.rainy_g
            )
            if (data.weather.contains("snowy")) imgSnowy.setImageResource(R.drawable.snowy) else imgSnowy.setImageResource(
                R.drawable.snowy_g
            )
            if (data.weather.contains("windy")) imgWindy.setImageResource(R.drawable.windy) else imgWindy.setImageResource(
                R.drawable.windy_g
            )

            //people
            if (data.people.contains("friend")) imgFriend.setImageResource(R.drawable.friend) else imgFriend.setImageResource(
                R.drawable.friend_g
            )
            if (data.people.contains("family")) imgFamily.setImageResource(R.drawable.family) else imgFamily.setImageResource(
                R.drawable.family_g
            )
            if (data.people.contains("coupleFriend")) imgCoupleFriend.setImageResource(R.drawable.couplefriend) else imgCoupleFriend.setImageResource(
                R.drawable.couplefriend_g
            )
            if (data.people.contains("businessFriend")) imgBuisnessFriend.setImageResource(R.drawable.businessfriend) else imgBuisnessFriend.setImageResource(
                R.drawable.businessfriend_g
            )
            if (data.people.contains("not")) imgNot.setImageResource(R.drawable.not) else imgNot.setImageResource(
                R.drawable.not_g
            )
            //school
            if (data.school.contains("classtime")) imgClasstime.setImageResource(R.drawable.classtime) else imgClasstime.setImageResource(
                R.drawable.classtime_g
            )
            if (data.school.contains("study")) imgStudy.setImageResource(R.drawable.study) else imgStudy.setImageResource(
                R.drawable.study_g
            )
            if (data.school.contains("assignment")) imgAssignment.setImageResource(R.drawable.assignment) else imgAssignment.setImageResource(
                R.drawable.assignment_g
            )
            if (data.school.contains("test")) imgTest.setImageResource(R.drawable.test) else imgTest.setImageResource(
                R.drawable.test_g
            )
            if (data.school.contains("team")) imgTeam.setImageResource(R.drawable.team) else imgTeam.setImageResource(
                R.drawable.team_g
            )
            //couple
            if (data.couple.contains("dateCouple")) imgDateCouple.setImageResource(R.drawable.datecouple) else imgDateCouple.setImageResource(
                R.drawable.datecouple_g
            )
            if (data.couple.contains("anniversary")) imgAnniversary.setImageResource(R.drawable.anniversary) else imgAnniversary.setImageResource(
                R.drawable.anniversary_g
            )
            if (data.couple.contains("gift")) imgGift.setImageResource(R.drawable.gift) else imgGift.setImageResource(
                R.drawable.gift_g
            )
            if (data.couple.contains("conflict")) imgConflict.setImageResource(R.drawable.conflict) else imgConflict.setImageResource(
                R.drawable.conflict_g
            )
            if (data.couple.contains("love")) imgLove.setImageResource(R.drawable.love) else imgLove.setImageResource(
                R.drawable.love_g
            )
            //eat
            if (data.eat.contains("breakfast")) imgBreakfast.setImageResource(R.drawable.breakfast) else imgBreakfast.setImageResource(
                R.drawable.breakfast_g
            )
            if (data.eat.contains("lunch")) imgLunch.setImageResource(R.drawable.lunch) else imgLunch.setImageResource(
                R.drawable.lunch_g
            )
            if (data.eat.contains("dinner")) imgDinner.setImageResource(R.drawable.dinner) else imgDinner.setImageResource(
                R.drawable.dinner_g
            )
            if (data.eat.contains("midnightSnack")) imgMidnightSnack.setImageResource(R.drawable.mid) else imgMidnightSnack.setImageResource(
                R.drawable.mid_g
            )
            //goods
            if (data.goods.contains("alcohol")) imgAlcohol.setImageResource(R.drawable.alcohol) else imgAlcohol.setImageResource(
                R.drawable.alcohol_g
            )
            if (data.goods.contains("smoking")) imgSmoking.setImageResource(R.drawable.smoking) else imgSmoking.setImageResource(
                R.drawable.smoking_g
            )
            if (data.goods.contains("coffee")) imgCoffee.setImageResource(R.drawable.coffee) else imgCoffee.setImageResource(
                R.drawable.coffee_g
            )
            if (data.goods.contains("snack")) imgSnack.setImageResource(R.drawable.snack) else imgSnack.setImageResource(
                R.drawable.snack_g
            )
            if (data.goods.contains("drink")) imgDrink.setImageResource(R.drawable.drink) else imgDrink.setImageResource(
                R.drawable.drink_g
            )


            deleteBoardID.setOnClickListener {
                listener.onDeleteBoardClickListener(data)
            }
            updateBoardID.setOnClickListener {
                listener.onUpdateBoardListener(data)
            }
        }
    }

    interface RowClickListener {
        fun onDeleteBoardClickListener(board: BoardEntity)
        fun onItemClickListener(board: BoardEntity)
        fun onUpdateBoardListener(board: BoardEntity)
    }
}