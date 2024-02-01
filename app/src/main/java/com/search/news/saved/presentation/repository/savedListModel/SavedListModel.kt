package com.search.news.saved.presentation.repository.savedListModel

import com.search.news.core.recycler.modelRecycler.NewsInterface

data class SavedListModel(
    override val urlToImage: String,
    override val title: String,
    override val content: String,
    override val publishedAt: String,
    override val source: String,
    override val description: String,
    override val url: String,
    override val idSource: String,
): NewsInterface