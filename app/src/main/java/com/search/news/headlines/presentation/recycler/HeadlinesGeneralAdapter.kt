package com.search.news.headlines.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.search.news.R
import com.search.news.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData

class HeadlinesGeneralAdapter(private val onClickAction: (article: HPresenterNewsModelData, position : Int) -> Unit) : ListAdapter<HPresenterNewsModelData, HeadlinesGeneralViewHolder>(
    HeadlinesDiffUtil
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesGeneralViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.headlines_recycler_view_item, parent, false)
        val viewHolder = HeadlinesGeneralViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            val position = viewHolder.adapterPosition
            onClickAction(item, position)

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: HeadlinesGeneralViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}