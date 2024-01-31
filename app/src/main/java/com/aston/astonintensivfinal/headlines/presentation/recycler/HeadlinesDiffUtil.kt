package com.aston.astonintensivfinal.headlines.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData

object HeadlinesDiffUtil: DiffUtil.ItemCallback<HPresenterNewsModelData>() {
    override fun areItemsTheSame(oldItem: HPresenterNewsModelData, newItem: HPresenterNewsModelData): Boolean {

        return oldItem.url == newItem.url

    }

    override fun areContentsTheSame(oldItem: HPresenterNewsModelData, newItem: HPresenterNewsModelData): Boolean {
        return oldItem.equals(newItem)
    }


}

