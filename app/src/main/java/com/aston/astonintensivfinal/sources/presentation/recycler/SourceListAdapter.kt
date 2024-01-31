package com.aston.astonintensivfinal.sources.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.sourseList.SourceNewsVM

class SourceListAdapter(private val onClickAction: (sourceNews: SourceNewsVM, position : Int) -> Unit) : ListAdapter<SourceNewsVM, SourceListViewHolder>(
    SourceDiffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.source_list_item_recycler, parent, false)
        val viewHolder = SourceListViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            val position = viewHolder.adapterPosition
            onClickAction(item, position)

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SourceListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }
}