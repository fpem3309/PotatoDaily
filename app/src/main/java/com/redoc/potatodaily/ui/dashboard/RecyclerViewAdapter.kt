package com.redoc.potatodaily.ui.dashboard

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.BoardItemBinding
import com.redoc.potatodaily.db.BoardEntity

class RecyclerViewAdapter(val listener : RowClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items = ArrayList<BoardEntity>()

    fun setListData(data: ArrayList<BoardEntity>) {
        this.items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            BoardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }

        holder.bind(items[position])
    }


    class MyViewHolder(private val binding: BoardItemBinding, val listener: RowClickListener) : RecyclerView.ViewHolder(binding.root) {

        private val tvTitle = binding.tvTitle
        private val tvContent = binding.tvContent


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


        private val deleteBoardID = binding.deleteBoardID

        fun bind(data: BoardEntity) {
            tvTitle.text = data.title
            tvContent.text = data.content
            //moood
            if (data.mood == "very_good"){
                imgMood.setImageResource(R.drawable.very_good)
            }else if(data.mood == "good"){
                imgMood.setImageResource(R.drawable.good)
            }else if(data.mood == "soso"){
                imgMood.setImageResource(R.drawable.soso)
            }else if(data.mood == "bad"){
                imgMood.setImageResource(R.drawable.bad)
            }else if(data.mood == "very_bad"){
                imgMood.setImageResource(R.drawable.very_bad)
            }else{
                imgMood.visibility = GONE
            }

            //weather
            if(data.weather.contains("sunny")){
                imgSunny.setImageResource(R.drawable.sunny)
            }
            if(data.weather.contains("cloudy")){
                imgCloudy.setImageResource(R.drawable.cloudy)
            }
            if(data.weather.contains("rainy")){
                imgRainy.setImageResource(R.drawable.rainy)
            }
            if(data.weather.contains("snowy")){
                imgSnowy.setImageResource(R.drawable.snowy)
            }
            if(data.weather.contains("windy")){
                imgWindy.setImageResource(R.drawable.windy)
            }

            //people
            if(data.people.contains("friend")){
                imgFriend.setImageResource(R.drawable.friend)
            }
            if(data.people.contains("family")){
                imgFamily.setImageResource(R.drawable.family)
            }
            if(data.people.contains("coupleFriend")){
                imgCoupleFriend.setImageResource(R.drawable.couplefriend)
            }
            if(data.people.contains("businessFriend")){
                imgBuisnessFriend.setImageResource(R.drawable.businessfriend)
            }
            if(data.people.contains("not")){
                imgNot.setImageResource(R.drawable.not)
            }
            //school
            if(data.school.contains("classtime")){
                imgClasstime.setImageResource(R.drawable.classtime)
            }
            if(data.school.contains("study")){
                imgStudy.setImageResource(R.drawable.study)
            }
            if(data.school.contains("assignment")){
                imgAssignment.setImageResource(R.drawable.assignment)
            }
            if(data.school.contains("test")){
                imgTest.setImageResource(R.drawable.test)
            }
            if(data.school.contains("team")){
                imgTeam.setImageResource(R.drawable.team)
            }
            //couple
            if(data.couple.contains("dateCouple")){
                imgDateCouple.setImageResource(R.drawable.datecouple)
            }
            if(data.couple.contains("anniversary")){
                imgAnniversary.setImageResource(R.drawable.anniversary)
            }
            if(data.couple.contains("gift")){
                imgGift.setImageResource(R.drawable.gift)
            }
            if(data.couple.contains("conflict")){
                imgConflict.setImageResource(R.drawable.conflict)
            }
            if(data.couple.contains("love")){
                imgLove.setImageResource(R.drawable.love)
            }
            //eat
            if(data.eat.contains("breakfast")){
                imgBreakfast.setImageResource(R.drawable.breakfast)
            }
            if(data.eat.contains("lunch")){
                imgLunch.setImageResource(R.drawable.lunch)
            }
            if(data.eat.contains("dinner")){
                imgDinner.setImageResource(R.drawable.dinner)
            }
            if(data.eat.contains("midnightSnack")){
                imgMidnightSnack.setImageResource(R.drawable.mid)
            }
            //goods
            if(data.goods.contains("alcohol")){
                imgAlcohol.setImageResource(R.drawable.alcohol)
            }
            if(data.goods.contains("smoking")){
                imgSmoking.setImageResource(R.drawable.smoking)
            }
            if(data.goods.contains("coffee")){
                imgCoffee.setImageResource(R.drawable.coffee)
            }
            if(data.goods.contains("snack")){
                imgSnack.setImageResource(R.drawable.snack)
            }
            if(data.goods.contains("drink")){
                imgDrink.setImageResource(R.drawable.drink)
            }


            deleteBoardID.setOnClickListener {
                listener.onDeleteBoardClickListener(data)
            }
        }
    }

    interface RowClickListener{
        fun onDeleteBoardClickListener(board: BoardEntity)
        fun onItemClickListener(board: BoardEntity)
    }
}