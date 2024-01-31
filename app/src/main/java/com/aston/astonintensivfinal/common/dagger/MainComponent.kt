package com.aston.astonintensivfinal.common.dagger

import com.aston.astonintensivfinal.common.presentation.ui.MainActivity
import dagger.Component

@Component(modules = [MainFactoryModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}