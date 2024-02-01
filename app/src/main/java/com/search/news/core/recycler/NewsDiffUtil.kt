package com.search.news.core.recycler

import androidx.recyclerview.widget.DiffUtil
import com.search.news.core.recycler.modelRecycler.NewsModel

object NewsDiffUtil:  DiffUtil.ItemCallback<NewsModel>() {
    override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.equals(newItem)
    }
}
