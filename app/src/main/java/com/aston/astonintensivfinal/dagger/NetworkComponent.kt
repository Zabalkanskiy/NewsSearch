package com.aston.astonintensivfinal.dagger

import com.aston.astonintensivfinal.AstonIntensivApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface NetworkComponent {
    fun inject(astonIntensivApplication: AstonIntensivApplication)
}