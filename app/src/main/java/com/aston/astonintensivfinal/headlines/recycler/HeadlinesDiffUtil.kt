package com.aston.astonintensivfinal.headlines.recycler

import androidx.recyclerview.widget.DiffUtil
import com.aston.astonintensivfinal.data.Article

object HeadlinesDiffUtil: DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        //maybe wrong
        return oldItem.url == newItem.url

    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.equals(newItem)
    }
}

