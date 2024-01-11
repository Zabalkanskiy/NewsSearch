package com.aston.astonintensivfinal.headlines.dagger

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.dagger.NetworkModule
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesFullNewsFragment
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesGeneralFragment
import dagger.Component

@Component(modules = [(HeadlinesModule::class), (HeadlinesFullNewsModule::class), (FullNewsFactoryModule::class)])
interface HeadlinesComponent {
    fun inject(headlinesGeneralFragment: HeadlinesGeneralFragment)
    fun inject(headlinesFullNewsFragment: HeadlinesFullNewsFragment)
}