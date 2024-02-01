package com.search.news

import android.app.Application
import com.search.news.dagger.AppComponent
import com.search.news.dagger.DaggerAppComponent
import com.search.news.core.data.databaseNews.NewsModel.NewsDao
import com.search.news.core.data.retrofit.NewsApiHeadlinesInterface
import javax.inject.Inject

class SearchApplication : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var headlinesApi: NewsApiHeadlinesInterface

    @Inject
    lateinit var newsDao: NewsDao

    override fun onCreate() {
        super.onCreate()
        getAstonApplicationContext = this

        appComponent = DaggerAppComponent.builder()
            .applicationContext(this)
            .build()

        appComponent.inject(this)

    }

    companion object {
        lateinit var getAstonApplicationContext: SearchApplication
            private set
    }
}