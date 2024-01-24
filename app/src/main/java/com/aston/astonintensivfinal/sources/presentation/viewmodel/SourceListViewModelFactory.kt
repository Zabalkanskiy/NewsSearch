package com.aston.astonintensivfinal.sources.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/*class SourceListViewModelFactory constructor(private val sourcesUseCase: GetSourcesUseCase) :
    ViewModelProvider.Factory {
}

 */

/*class EditTextFragmentViewModelFactory(private val editTextFragmentRepository: EditTextFragmentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTextFragmentViewModel::class.java)) {
            return EditTextFragmentViewModel(editTextFragmentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

 */

class   SourceListViewModelFactory @Inject constructor(private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = viewModels[modelClass]?.get()
        return requireNotNull(provider as T) {
            "view model not found: $modelClass"
        }
    }
}
