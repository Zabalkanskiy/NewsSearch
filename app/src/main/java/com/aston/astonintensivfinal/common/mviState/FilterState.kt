package com.aston.astonintensivfinal.common.mviState

import java.util.Date

data class FilterState(
    val language: Language,
    val date: DateRange? ,
    val sort: Sort,
    val openDateRangePicker: Boolean = false

){






}

