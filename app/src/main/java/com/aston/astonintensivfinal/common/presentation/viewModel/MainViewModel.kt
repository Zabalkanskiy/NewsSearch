package com.aston.astonintensivfinal.common.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.astonintensivfinal.common.mviState.FilterState
import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.common.mviState.Sort
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
    private val stateShared: MutableLiveData<FilterState> = MutableLiveData(FilterState(language = Language.English, date = null, sort = Sort.New))

    val getStateShared: LiveData<FilterState> = stateShared

    fun changeSharedState(filterState: FilterState){
        stateShared.postValue(filterState)
    }
}