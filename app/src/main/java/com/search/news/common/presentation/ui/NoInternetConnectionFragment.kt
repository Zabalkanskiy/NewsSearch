package com.search.news.common.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.search.news.R

class NoInternetConnectionFragment : Fragment() {
    lateinit var refreshImageButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.network_error_screen_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshImageButton = view.findViewById(R.id.network_error_refresh_button)
        refreshImageButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    companion object {
        fun newInstance(): NoInternetConnectionFragment {

            return NoInternetConnectionFragment()


        }
    }
}