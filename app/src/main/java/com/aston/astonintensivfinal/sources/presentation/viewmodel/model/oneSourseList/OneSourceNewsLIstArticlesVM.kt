package com.aston.astonintensivfinal.sources.presentation.viewmodel.model.oneSourseList

import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle

data class OneSourceNewsLIstArticlesVM(val totalResults: Int, val articles: List<OneSourceNewsArticleVM>) : OneSourceNewsVM()
