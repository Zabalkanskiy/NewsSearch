package com.search.news.common.dagger

import com.search.news.common.mviState.FilterState
import com.search.news.common.presentation.ui.FilterFragment
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