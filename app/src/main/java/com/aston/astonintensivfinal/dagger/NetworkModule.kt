package com.aston.astonintensivfinal.dagger

import com.aston.astonintensivfinal.data.ApiResponse
import com.aston.astonintensivfinal.data.ApiResponseDeserializer
import com.aston.astonintensivfinal.data.retrofit.NewsApiHeadlinesInterface
import com.aston.astonintensivfinal.data.sourcemodel.SourceResponceDeserializer
import com.aston.astonintensivfinal.data.sourcemodel.SourceResponse
import com.aston.astonintensivfinal.utils.Utils
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitHeadlinesRxJava():Retrofit{
        val client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
        val gson = GsonBuilder()
            .registerTypeAdapter(ApiResponse::class.java, ApiResponseDeserializer())
            .registerTypeAdapter(SourceResponse::class.java, SourceResponceDeserializer())
            .create()
        return Retrofit.Builder()
            .baseUrl(Utils.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providerRetrofitSources(): Retrofit{
        val httpClient = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(ApiResponse::class.java, ApiResponseDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(Utils.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()

    }

    //point rxJava Headlines News
    @Singleton
    @Provides
    fun getHeadlinesRxJava() = provideRetrofitHeadlinesRxJava().create(NewsApiHeadlinesInterface::class.java)
}