package com.aston.astonintensivfinal.data.retrofit

import androidx.lifecycle.LiveData
import com.aston.astonintensivfinal.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiSourcesInterface {

    //package sources

    @GET("top-headlines/sources")
   suspend fun getListAllSources(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20) : Response<ApiResponse>

    @GET("top-headlines/sources")
  suspend  fun getListOneSource(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20, @Query("description" )description: String) : Response<ApiResponse>
    //not work now
    @GET("top-headlines/sources")
  suspend  fun getFilterSource(@Query("apiKey") apiKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20) : Response<ApiResponse>
}