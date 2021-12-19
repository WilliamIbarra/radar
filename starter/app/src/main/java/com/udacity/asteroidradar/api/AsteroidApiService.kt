package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsData
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

// This variable enable to convert a Json object to Kotlin object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val  okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)

// Retrofit variable with BASE_URL for manage requests to the Api.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient.build())
    .baseUrl(BASE_URL)
    .build()
// Interface to create requests to the Api
interface AsteroidApiService {


    @GET("planetary/apod?api_key=$API_KEY")
    fun getPictureOfDay():
            Call<PictureOfDay>


    @GET("neo/rest/v1/feed?start_date=2021-12-03&end_date=2021-12-10&api_key=$API_KEY")
    fun getAsteroids():
            Call<AsteroidsData>

}

// Public object for make requests in other classes
object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}



