package com.udacity.asteroidradar.Network

import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * retrofit need 2 things in order to work :
 * 1- Base Url
 * 2- Converter factory which is here Scalars...
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object NasaApi {
    val nasaService: NasaServiceApi by lazy {
        retrofit.create(NasaServiceApi::class.java)
    }
}