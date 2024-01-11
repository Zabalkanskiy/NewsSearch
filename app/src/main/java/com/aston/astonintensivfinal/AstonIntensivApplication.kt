package com.aston.astonintensivfinal

import android.app.Application
import com.aston.astonintensivfinal.dagger.DaggerAppComponent
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.data.retrofit.NewsApiHeadlinesInterface
import javax.inject.Inject

class AstonIntensivApplication : Application() {
   // lateinit var appComponent: NetworkComponent

    @Inject
    lateinit var headlinesApi: NewsApiHeadlinesInterface

    @Inject
    lateinit var newsDao: NewsDao

    override fun onCreate() {
        super.onCreate()
        getAstonApplicationContext = this
     //  appComponent = DaggerNetworkComponent.builder().build()
      //  appComponent.inject(this)
       // appComponent.inject(this)
        DaggerAppComponent.builder()
            .applicationContext(this)
            .build()
            .inject(this)

    }

    companion object {
        lateinit var getAstonApplicationContext: AstonIntensivApplication
            private set
    }
}