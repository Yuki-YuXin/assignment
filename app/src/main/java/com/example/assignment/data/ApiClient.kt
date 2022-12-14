package com.example.assignment.data

import com.example.assignment.model.MeteorData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


object ApiClient {

    private const val API_BASE_URL = "https://data.nasa.gov/"

    private var servicesApiInterface: MeteorApiInterface? = null

    fun build(): MeteorApiInterface {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            MeteorApiInterface::class.java
        )

        return servicesApiInterface as MeteorApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface MeteorApiInterface {
        @GET("resource/y77d-th95.json?\$where=year>='1900-01-01T00:00:00.000'")//&\$limit=15")
        fun getMeteorsJsonData(): Call<List<MeteorData>>
    }
}