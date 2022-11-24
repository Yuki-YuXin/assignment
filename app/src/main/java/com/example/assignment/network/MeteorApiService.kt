package com.example.assignment.network

import com.example.assignment.model.MeteorData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://data.nasa.gov/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a com.example.assignment.network.retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getMeteorsInfo] method
 */
interface MeteorsApiService {
    /**
     * Returns a [MutableList] of [MeteorData] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("resource/y77d-th95.json?\$where=year>='1900-01-01T00:00:00.000'")//&\$limit=15")
    suspend fun getMeteorsInfo(): MutableList<MeteorData>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MeteorsApi {
    val retrofitService: MeteorsApiService by lazy {
        retrofit.create(MeteorsApiService::class.java)
    }
}