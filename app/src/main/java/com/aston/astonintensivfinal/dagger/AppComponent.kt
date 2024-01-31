package com.aston.astonintensivfinal.dagger

import android.content.Context
import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.core.data.retrofit.NewsApiHeadlinesInterface
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataBaseModule::class])
interface AppComponent {
    fun getHeadlinesRxJava(): NewsApiHeadlinesInterface
    fun inject(astonIntensivApplication: AstonIntensivApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): AppComponent
    }
}