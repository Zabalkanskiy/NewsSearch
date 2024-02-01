package com.search.news.common.presentation.viewModel

import androidx.core.util.Pair
import androidx.lifecycle.ViewModel
import com.search.news.common.mviState.DateRange
import com.search.news.common.mviState.FilterState
import com.search.news.common.mviState.Language
import com.search.news.common.mviState.Sort
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class FilterViewModel @Inject constructor(initialState: FilterState) : ContainerHost<FilterState, Nothing>, ViewModel() {


    override val container: Container<FilterState, Nothing> = container(initialState)

    fun changeLanguage(language: Language) =

         intent {

            reduce { state.copy(language = language) }
        }


    fun changeSort(newSort: Sort) = intent {

        reduce { state.copy(sort = newSort) }
    }

    fun changeDate(newDateRange: Pair<Long, Long>) = intent {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)


        val startDateString = sdf.format(Date(newDateRange.first))
        val endDateString = sdf.format(Date(newDateRange.second))

        reduce { state.copy(date = DateRange(start = startDateString, end = endDateString)) }
    }


    fun openDatePicker( ) = intent{
        reduce { state.copy(openDateRangePicker = true) }
    }

    fun closeDatePicker() = intent {
        reduce { state.copy(openDateRangePicker = false) }
    }
}