package com.aston.astonintensivfinal

import android.app.Application
import com.aston.astonintensivfinal.dagger.DaggerNetworkComponent
import com.aston.astonintensivfinal.dagger.NetworkComponent
import com.aston.astonintensivfinal.data.retrofit.NewsApiHeadlinesInterface
import javax.inject.Inject

class AstonIntensivApplication : Application() {
   // lateinit var appComponent: NetworkComponent

    @Inject
    lateinit var headlinesApi: NewsApiHeadlinesInterface

    override fun onCreate() {
        super.onCreate()
        getAstonApplicationContext = this
     //  appComponent = DaggerNetworkComponent.builder().build()
      //  appComponent.inject(this)
       // appComponent.inject(this)
        DaggerNetworkComponent.builder()
            .build()
            .inject(this)

    }

    companion object {
        lateinit var getAstonApplicationContext: AstonIntensivApplication
            private set
    }
}