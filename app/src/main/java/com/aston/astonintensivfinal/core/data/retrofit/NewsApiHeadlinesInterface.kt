package com.aston.astonintensivfinal.core.data.retrofit

import androidx.lifecycle.LiveData
import com.aston.astonintensivfinal.core.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.core.data.sourcemodel.SourceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



const val NEWSAPIKEY : String = "0dc9dbbcf5a74911ab846d7ea7d299af"


interface NewsApiHeadlinesInterface {


    @GET("top-headlines")
   fun getGeneralHeadlines(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("category" ) category: String = "general", @Query("language") language: String = "en", @Query("country") country: String = "us", @Query("q") query: String = "") : Single<ApiResponse>

    @GET("everything")
    fun getGeneralFromEverything(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("q") search: String = "", @Query("from") from: String = "", @Query("to") to: String = "", @Query("sortBy") sortBy: String = "", @Query("language") language: String = ""): Single<ApiResponse>


    @GET("top-headlines/sources")
    suspend  fun getFilterSource(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("language") language: String = "") : SourceResponse



    @GET("everything")
   suspend fun getNewsWithParametr(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("sources") sources: String = "abc-news", @Query("q") search: String = "", @Query("from") from: String = "", @Query("to") to: String = "", @Query("sortBy") sortBy: String = "", @Query("language") language: String = "") : ApiResponse







}