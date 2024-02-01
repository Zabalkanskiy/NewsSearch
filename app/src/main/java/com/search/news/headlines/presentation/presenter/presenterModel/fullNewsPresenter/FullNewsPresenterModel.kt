package com.search.news.headlines.presentation.presenter.presenterModel.fullNewsPresenter

import android.os.Parcelable
import com.search.news.core.recycler.modelRecycler.NewsInterface
import kotlinx.parcelize.Parcelize

@Parcelize
data class FullNewsPresenterModel(
     override val urlToImage: String,
     override val title: String,
     override val content: String,
     override val publishedAt: String,
     override val source: String,
     override val description: String,
     override val url: String,
     override val idSource:String
): Parcelable, NewsInterface