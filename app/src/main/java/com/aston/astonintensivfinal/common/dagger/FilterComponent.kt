package com.aston.astonintensivfinal.common.dagger

import android.content.Context
import com.aston.astonintensivfinal.common.mviState.FilterState
import com.aston.astonintensivfinal.common.presentation.ui.FilterFragment
import com.aston.astonintensivfinal.dagger.AppComponent
import dagger.BindsInstance
import dagger.Component

@Component(modules = [FilterFactoryModule::class])
interface FilterComponent {
    fun inject(filterFragment: FilterFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun filterState(filterState: FilterState): Builder
        fun build(): FilterComponent
    }
}