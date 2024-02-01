package com.search.news.sources.presentation.viewmodel.model.oneSourseList

import com.search.news.core.recycler.modelRecycler.NewsInterface

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
