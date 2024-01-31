package com.aston.astonintensivfinal.core.recycler.modelRecycler

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    val source: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
): Parcelable



