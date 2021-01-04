package com.udacity.asteroidradar.api

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.domain.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaServiceApi {

    //Retrieve a list of Asteroids based on their closest approach date to Earth
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Response


    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String): PictureOfDay

}