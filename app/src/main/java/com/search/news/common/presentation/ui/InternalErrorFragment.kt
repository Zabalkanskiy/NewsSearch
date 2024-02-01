package com.search.news.common.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.search.news.R

class InternalErrorFragment : Fragment() {
    lateinit var refreshImageButton: ImageButton
    lateinit var internalErrorToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.internal_error_screen_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshImageButton = view.findViewById(R.id.internal_error_refresh_button)
        internalErrorToolbar = view.findViewById(R.id.internal_error_toolbar)
        refreshImageButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        internalErrorToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        internalErrorToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    companion object {
        fun newInstance(): InternalErrorFragment {

            return InternalErrorFragment()


        }
    }
}