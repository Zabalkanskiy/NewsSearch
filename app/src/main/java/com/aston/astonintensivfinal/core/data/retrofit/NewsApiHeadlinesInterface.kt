package com.aston.astonintensivfinal.core.data.retrofit

import androidx.lifecycle.LiveData
import com.aston.astonintensivfinal.core.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.core.data.sourcemodel.SourceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// new api key: String = 13ce251ce6e0423897099eecfec4ed2f
// old api key: String = 0dc9dbbcf5a74911ab846d7ea7d299af
const val NEWSAPIKEY : String = "0dc9dbbcf5a74911ab846d7ea7d299af"


interface NewsApiHeadlinesInterface {

    //package headlines
    @GET("top-headlines")
   fun getGeneralHeadlines(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("category" ) category: String = "general", @Query("language") language: String = "en", @Query("country") country: String = "us", @Query("q") query: String = "") : Single<ApiResponse>

    @GET("everything")
    fun getGeneralFromEverything(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("q") search: String = "", @Query("from") from: String = "", @Query("to") to: String = "", @Query("sortBy") sortBy: String = "", @Query("language") language: String = ""): Single<ApiResponse>

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
    //откидывает параметры page и pageSize не нужны
    @GET("top-headlines/sources")
    suspend  fun getFilterSource(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("language") language: String = "") : SourceResponse


    //One Source List News

    @GET("everything")
   suspend fun getNewsFromOneSource(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("sources") sources: String = "abc-news", @Query("q") search: String = "") : ApiResponse

    @GET("everything")
   suspend fun getNewsWithParametr(@Query("apiKey") apiKey: String,@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("sources") sources: String = "abc-news", @Query("q") search: String = "", @Query("from") from: String = "", @Query("to") to: String = "", @Query("sortBy") sortBy: String = "", @Query("language") language: String = "") : ApiResponse

   // https://newsapi.org/v2/everything/?apiKey=0dc9dbbcf5a74911ab846d7ea7d299af&page=1&pageSize=20&language=en&sources=abc-news


    //package saved
    //need save local

 //   @GET("")
 //   fun getListSaved(@Query("page") page: Int, @Query("pageSize") pageSize: Int = 20) : LiveData<ApiResponse>





}