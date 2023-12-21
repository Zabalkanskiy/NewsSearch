package com.aston.astonintensivfinal.headlines.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HeadlinesFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HeadlinesGeneralFragment.newInstance()
            1 -> HeadlinesBusinessFragment.newInstance()
            else -> HeadlinesTravelFragment.newInstance()
        }
    }
}