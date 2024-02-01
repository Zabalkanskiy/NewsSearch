package com.search.news.sources.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.search.news.sources.presentation.viewmodel.model.sourseList.SourceNewsVM

object SourceDiffUtil:  DiffUtil.ItemCallback<SourceNewsVM>() {
    override fun areItemsTheSame(oldItem: SourceNewsVM, newItem: SourceNewsVM): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SourceNewsVM, newItem: SourceNewsVM): Boolean {
        return oldItem.equals(newItem)
    }
}