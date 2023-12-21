package com.aston.astonintensivfinal.headlines.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.aston.astonintensivfinal.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.String
import kotlin.arrayOf
import kotlin.intArrayOf


class Headlines_view_pager : Fragment(){

    lateinit var headlinesTabLayout: TabLayout

    lateinit var headlinesViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.headlines_view_pager_and_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headlinesTabLayout = view.findViewById(R.id.headlines_tab_layout)
        headlinesViewPager = view.findViewById(R.id.headlines_view_pager)

        val headlinesFragmentStateAdapter = HeadlinesFragmentStateAdapter(this)
        headlinesViewPager.adapter = headlinesFragmentStateAdapter

        val tabIcons = intArrayOf(R.drawable.world_sphere, R.drawable.coin_hand, R.drawable.atom)
        val tabTexts = arrayOf("General", "Business", "Travelling")

     //   for (i in tabIcons.indices) {
   //         val tab: TabLayout.Tab = headlinesTabLayout.newTab()
    //        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_tabs, null)
    //        val icon = view.findViewById<ImageView>(R.id.headlines_custom_tab_image_view)
     //       val text = view.findViewById<TextView>(R.id.headlines_custom_tab_textView)
     //       icon.setImageResource(tabIcons[i])
     //       text.text = tabTexts[i]

     //       tab.setCustomView(view)
     //       headlinesTabLayout.addTab(tab)
     //   }

        TabLayoutMediator(headlinesTabLayout, headlinesViewPager) { tab, position ->

            // Inflate the custom tab item layout
            val tabView = LayoutInflater.from(context).inflate(R.layout.custom_tabs, null)
            // Find the views in the custom layout
            val icon = tabView.findViewById<ImageView>(R.id.headlines_custom_tab_image_view)
            val text = tabView.findViewById<TextView>(R.id.headlines_custom_tab_textView)

            // Set the icon and text for the tab
            icon.setImageResource(tabIcons[position])
            text.text = tabTexts[position]
            text.setTextColor(ContextCompat.getColorStateList(context as Context, R.color.text_selector))


            // Set the custom view for the tab
            tab.customView = tabView

        }.attach()

    }

    companion object {
        fun newInstance(): Headlines_view_pager{
            return Headlines_view_pager()
        }
    }
}