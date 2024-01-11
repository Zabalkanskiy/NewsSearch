package com.aston.astonintensivfinal.data.retrofit

import androidx.lifecycle.LiveData
import com.aston.astonintensivfinal.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.data.sourcemodel.SourceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiHeadlinesInterface {

    //package headlines
    @GET("top-headlines")
   fun getGeneralHeadlines(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("category" ) category: String = "general", @Query("language") language: String = "en", @Query("country") country: String = "us") : Single<ApiResponse>

    @GET("top-headlines")
    fun getFilterGeneralHeadlines(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("q") query: String, @Query("category" ) category: String = "general") : Single<ApiResponse>


   @GET("top-headlines")
    fun getBusinessHeadlines(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("category" ) category: String = "business") : Single<ApiResponse>

    @GET("top-headlines")
    fun getFilterBusinessHeadlines(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("q") query: String, @Query("category" ) category: String = "business") : Single<ApiResponse>

    @GET("top-headlines")
    fun getTravellingHeadlines(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("q") query: String = "travel") : Single<ApiResponse>

    @GET("top-headlines")
    fun getFilterTravellingHeadlines(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("q") query: String) : LiveData<ApiResponse>


    //sources

    @GET("top-headlines/sources")
    suspend fun getListAllSources(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20) : Response<SourceResponse>

    @GET("top-headlines/sources")
    suspend  fun getListOneSource(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("description" )description: String) : Response<SourceResponse>
    //not work now
    @GET("top-headlines/sources")
    suspend  fun getFilterSource(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20) : SourceResponse


    //package saved
    //need save local

 //   @GET("")
 //   fun getListSaved(@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20) : LiveData<ApiResponse>





}