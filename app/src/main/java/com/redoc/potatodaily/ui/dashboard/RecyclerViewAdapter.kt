package com.redoc.potatodaily.ui.dashboard

import android.view.LayoutInflater
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

        val tvTitle = binding.tvTitle
        val tvContent = binding.tvContent

        val imgMood = binding.imgMood
        val imgSunny = binding.imgSunny
        val imgCloudy = binding.imgCloudy
        val imgRainy = binding.imgRainy
        val imgSnowy = binding.imgSnowy
        val imgWindy = binding.imgWindy

        val deleteBoardID = binding.deleteBoardID
        fun bind(data: BoardEntity) {
            tvTitle.text = data.title
            tvContent.text = data.content
            if (data.mood == "good"){
                imgMood.setImageResource(R.drawable.test)
            }

            if(data.weather.contains("sunny")){
                imgSunny.setImageResource(R.drawable.test)
            }
            if(data.weather.contains("cloudy")){
                imgCloudy.setImageResource(R.drawable.test)
            }
            if(data.weather.contains("rainy")){
                imgRainy.setImageResource(R.drawable.test)
            }
            if(data.weather.contains("snowy")){
                imgSnowy.setImageResource(R.drawable.test)
            }
            if(data.weather.contains("windy")){
                imgWindy.setImageResource(R.drawable.test)
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