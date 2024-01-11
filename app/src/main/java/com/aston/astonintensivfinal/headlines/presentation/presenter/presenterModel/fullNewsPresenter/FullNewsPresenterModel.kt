package com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.fullNewsPresenter

data class FullNewsPresenterModel(
     val urlToImage: String,
     val titleNews: String,
     val content: String,
     val publishedAt: String,
     val source: String
)