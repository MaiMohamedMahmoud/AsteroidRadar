package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.DateUtil.currentDate
import com.udacity.asteroidradar.Network.NasaApi
import com.udacity.asteroidradar.Network.parseAsteriodResponseToList
import com.udacity.asteroidradar.database.AsteriodDAO
import com.udacity.asteroidradar.database.Entity
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.DomainAsteriod
import com.udacity.asteroidradar.domain.convertDBObjectToDomain
import com.udacity.asteroidradar.domain.convertToDatabaseObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MainViewRepository(val asteriodDAO: AsteriodDAO) {

    suspend fun refreshDatabase() {
        withContext(Dispatchers.IO) {
            val responseObj =
                NasaApi.nasaService.getAsteroids(
                    currentDate(),
                    currentDate(DEFAULT_END_DATE_DAYS),
                    API_KEY
                )
            val list = parseAsteriodResponseToList(responseObj.near_earth_objects)
            asteriodDAO.insertAsteriod(* convertToDatabaseObject(list))
        }
    }

    suspend fun getAllAsteriodList(): List<DomainAsteriod> {
        return convertDBObjectToDomain(
            withContext(Dispatchers.IO) {
                asteriodDAO.getAllAsteriod(
                    currentDate(),
                    currentDate(DEFAULT_END_DATE_DAYS)
                )
            }

        )
    }

    suspend fun getSavedAsteriodList(): List<DomainAsteriod> {
        return convertDBObjectToDomain(
            withContext(Dispatchers.IO) { asteriodDAO.getSavedAsteriod() }
        )
    }

    suspend fun getTodayAsteriodList(): List<DomainAsteriod> {
        return convertDBObjectToDomain(
            withContext(Dispatchers.IO) {
                asteriodDAO.getTodayAsteriod(
                    currentDate()
                )
            }
        )
    }

    suspend fun callAsteriodbyFilter(value: String): List<DomainAsteriod> {
        if (value == "week") {
            Log.i("yarab", "week")
            return getAllAsteriodList()

        } else if (value == "today") {
            Log.i("yarab", "today")
            return getTodayAsteriodList()
        } else
            Log.i("yarab", "saved")
        return getSavedAsteriodList()

    }

}