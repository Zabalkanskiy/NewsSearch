package com.search.news.dagger

import android.content.Context
import com.search.news.SearchApplication
import com.search.news.core.data.retrofit.NewsApiHeadlinesInterface
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataBaseModule::class])
interface AppComponent {
    fun getHeadlinesRxJava(): NewsApiHeadlinesInterface
    fun inject(searchApplication: SearchApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): AppComponent
    }
}