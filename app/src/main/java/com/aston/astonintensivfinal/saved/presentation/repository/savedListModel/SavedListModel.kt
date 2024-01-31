package com.aston.astonintensivfinal.saved.presentation.repository.savedListModel

import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsInterface
import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsModel

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