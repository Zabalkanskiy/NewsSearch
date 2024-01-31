package com.aston.astonintensivfinal.headlines.domain.model.FullNewsDomain

data class FullNewsDomainModel(
    val urlToImage:String ,
    val title: String,
    val content: String,
    val publishedAt: String,
    val sourceName: String,
    val description: String,
    val url: String,
    val idSource:String
)