package com.search.news.core.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.search.news.R
import com.search.news.core.recycler.modelRecycler.NewsInterface

class NewsAdapter(private val onClickAction: (article: NewsInterface, position : Int) -> Unit): ListAdapter<NewsInterface, NewsViewHolder>(NewDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.headlines_recycler_view_item, parent, false)
        val viewHolder = NewsViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            val position = viewHolder.adapterPosition
            onClickAction(item, position)

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

