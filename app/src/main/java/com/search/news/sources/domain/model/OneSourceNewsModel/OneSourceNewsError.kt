package com.search.news.sources.domain.model.OneSourceNewsModel

data class OneSourceNewsError(
    val status: String,

    val code: String,

    val message: String
): OneSourceNewsDomain()
