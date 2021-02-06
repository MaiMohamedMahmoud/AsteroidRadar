package com.udacity.asteroidradar.main

import android.util.Log
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.DateUtil.currentDate
import com.udacity.asteroidradar.Network.AsteriodRemoteDataSource
import com.udacity.asteroidradar.Network.NasaApi
import com.udacity.asteroidradar.Network.parseAsteriodResponseToList
import com.udacity.asteroidradar.database.AsteriodDAO
import com.udacity.asteroidradar.domain.DomainAsteriod
import com.udacity.asteroidradar.domain.convertDBObjectToDomain
import com.udacity.asteroidradar.domain.convertToDatabaseObject
import com.udacity.asteroidradar.performGetOperation
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
            val list =
                responseObj.body()?.near_earth_objects?.let { parseAsteriodResponseToList(it) }
            //if list is not null then call fun convertToDatabaseObject and if this fun return is not null then insert it in the db ..
            //same as this asteriodDAO.insertAsteriod(* convertToDatabaseObject(list))
            list?.let { convertToDatabaseObject(it) }?.let { asteriodDAO.insertAsteriod(* it) }
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
        when (value) {
            "week" -> {
                Log.i("yarab", "week")
                return getAllAsteriodList()

            }
            "today" -> {
                Log.i("yarab", "today")
                return getTodayAsteriodList()
            }
            else -> Log.i("yarab", "saved")
        }
        return getSavedAsteriodList()

    }


//    fun getCharacters() = performGetOperation(
//        databaseQuery = {
//            convertDBObjectToDomain(asteriodDAO.getAllAsteriod(
//                currentDate(),
//                currentDate(DEFAULT_END_DATE_DAYS)
//            ))
//        },
//        networkCall = { AsteriodRemoteDataSource().getAsteroids() },
//        saveCallResult = { asteriodDAO.insertAsteriod(it.results) }
//    )

}
