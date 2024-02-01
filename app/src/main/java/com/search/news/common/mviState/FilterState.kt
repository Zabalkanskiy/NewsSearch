package com.search.news.common.mviState

data class FilterState(
    val language: Language,
    val date: DateRange? ,
    val sort: Sort,
    val openDateRangePicker: Boolean = false

){






}

