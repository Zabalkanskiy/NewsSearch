package com.aston.astonintensivfinal.core.recycler

import androidx.recyclerview.widget.DiffUtil
import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsModel
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData

object NewsDiffUtil:  DiffUtil.ItemCallback<NewsModel>() {
    override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.equals(newItem)
    }
}
