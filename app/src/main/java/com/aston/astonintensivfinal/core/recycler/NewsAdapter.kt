package com.aston.astonintensivfinal.core.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsInterface
import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsModel
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData
import com.aston.astonintensivfinal.headlines.presentation.recycler.HeadlinesDiffUtil
import com.aston.astonintensivfinal.headlines.presentation.recycler.HeadlinesGeneralViewHolder

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

