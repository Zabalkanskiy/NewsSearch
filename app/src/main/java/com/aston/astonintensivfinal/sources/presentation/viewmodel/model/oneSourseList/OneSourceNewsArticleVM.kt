package com.aston.astonintensivfinal.sources.presentation.viewmodel.model.oneSourseList

import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsInterface
import com.aston.astonintensivfinal.data.headlinesmodel.Source

class OneSourceNewsArticleVM(
    override val source: String,

    val author: String,


    override val title: String,


    override val description: String,


    override val url: String,


    override val urlToImage: String,


    override val publishedAt: String,

    override val content: String,

    override val idSource: String,
): NewsInterface
