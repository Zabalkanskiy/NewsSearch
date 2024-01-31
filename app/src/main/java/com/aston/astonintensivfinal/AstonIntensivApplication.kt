package com.aston.astonintensivfinal

import android.app.Application
import com.aston.astonintensivfinal.dagger.AppComponent
import com.aston.astonintensivfinal.dagger.DaggerAppComponent
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.core.data.retrofit.NewsApiHeadlinesInterface
import javax.inject.Inject

class AstonIntensivApplication : Application() {
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
        lateinit var getAstonApplicationContext: AstonIntensivApplication
            private set
    }
}