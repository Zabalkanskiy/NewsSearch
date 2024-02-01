package com.search.news.dagger

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.search.news.core.data.headlinesmodel.ApiResponse
import com.search.news.core.data.headlinesmodel.ApiResponseDeserializer
import com.search.news.core.data.retrofit.NewsApiHeadlinesInterface
import com.search.news.core.data.sourcemodel.SourceResponceDeserializer
import com.search.news.core.data.sourcemodel.SourceResponse
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASEURL : String = "https://newsapi.org/v2/"


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(context: Context): Retrofit {
        val cacheDir = File(context.getDir("my_cache_dir", Context.MODE_PRIVATE), "my_cache")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        val cacheSize = 30 * 1024 * 1024 // 30 MB
        val cache = Cache(cacheDir, cacheSize.toLong())

        fun hasNetwork(context: Context): Boolean? {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        }

         fun provideOfflineCacheInterceptor(context: Context): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()
                var cacheHeaderValue = if (!hasNetwork(context)!!){
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 1
                } else {
                    "public, max-age=" + 5
                }
                request = request.newBuilder().header("Cache-Control", cacheHeaderValue).build()
                chain.proceed(request)
            }
        }

         fun provideCacheInterceptor(context: Context): Interceptor {
            return Interceptor { chain ->
                val request = chain.request()
                var cacheHeaderValue = if (!hasNetwork(context)!!){
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 1
                } else {
                    "public, max-age=" + 5
                }

                val response = chain.proceed(request)
                response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build()
            }
        }


        val client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(provideOfflineCacheInterceptor(context = context))
            .addNetworkInterceptor(provideCacheInterceptor(context))
            .cache(cache)
            .build()


        val gson = GsonBuilder()
            .registerTypeAdapter(ApiResponse::class.java, ApiResponseDeserializer())
            .registerTypeAdapter(SourceResponse::class.java, SourceResponceDeserializer())
            .create()
        return Retrofit.Builder()
            .baseUrl(BASEURL)

            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }



    @Singleton
    @Provides
    fun getHeadlinesRxJava(context: Context): NewsApiHeadlinesInterface =
        provideRetrofit(context = context).create(NewsApiHeadlinesInterface::class.java)
}