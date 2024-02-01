package com.search.news.sources.domain.model.sourceListModel

class NewsSourceErrorResponseDomain(
    val status: String? ,


    val code: String? ,


    val message: String? ) : SourceResponseDomain()