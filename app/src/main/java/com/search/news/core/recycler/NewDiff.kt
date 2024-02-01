package com.search.news.core.recycler

import androidx.recyclerview.widget.DiffUtil
import com.search.news.core.recycler.modelRecycler.NewsInterface

object NewDiff: DiffUtil.ItemCallback<NewsInterface>() {
    override fun areItemsTheSame(oldItem: NewsInterface, newItem: NewsInterface): Boolean {

        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsInterface, newItem: NewsInterface): Boolean {

        return oldItem.source == newItem.source &&
                oldItem.title == newItem.title &&
                oldItem.description == newItem.description &&
                oldItem.url == newItem.url &&
                oldItem.urlToImage == newItem.urlToImage &&
                oldItem.publishedAt == newItem.publishedAt &&
                oldItem.content == newItem.content
    }
}
