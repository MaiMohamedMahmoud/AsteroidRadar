package com.udacity.asteroidradar.Network

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.DateUtil

class AsteriodRemoteDataSource : BaseDataSource() {

    suspend fun getAsteroids() = getResult {
        NasaApi.nasaService.getAsteroids(
            DateUtil.currentDate(),
            DateUtil.currentDate(Constants.DEFAULT_END_DATE_DAYS),
            Constants.API_KEY
        )
    }

    suspend fun getPictureOfDay()= getResult { NasaApi.nasaService.getPictureOfDay(Constants.API_KEY) }
}