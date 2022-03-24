package com.redoc.potatodaily.ui.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

    class PagerRecyclerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

        var fragments : ArrayList<Fragment> = ArrayList()

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
            notifyItemInserted(fragments.size-1)
        }

        fun removeFragment() {
            fragments.removeLast()
            notifyItemRemoved(fragments.size)
        }




//    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val pageName: TextView = itemView.findViewById(R.id.pageName)
//
//        fun bind(@ColorRes bgColor: Int, position: Int) {
//            pageName.text = "Page ${position+1}"
//            pageName.setBackgroundColor(ContextCompat.getColor(pageName.context, bgColor))
//
//        }
//    }
    }
