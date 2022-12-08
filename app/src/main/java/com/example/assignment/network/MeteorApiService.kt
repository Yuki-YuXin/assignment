package com.example.assignment.network

import com.example.assignment.model.MeteorData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://data.nasa.gov/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MeteorsApiService {
    @GET("resource/y77d-th95.json?\$where=year>='1900-01-01T00:00:00.000'")//&\$limit=15")
    suspend fun getMeteorsInfo(): List<MeteorData>
}

object MeteorsApi {
    val retrofitService: MeteorsApiService by lazy {
        retrofit.create(MeteorsApiService::class.java)
    }
}