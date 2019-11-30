package com.android.damir.jobfind.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface JobApiService{
    @Headers("User-Agent: JobFindPlatform/1.0")
    @GET("vacancies?area=160")
    fun getVacanciesAsync(@Query("page")page: Int, @Query("text")text: String): Deferred<ApiResponse>
}

object JobApiNetwork{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.hh.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val apiResponse = retrofit.create(JobApiService::class.java)
}