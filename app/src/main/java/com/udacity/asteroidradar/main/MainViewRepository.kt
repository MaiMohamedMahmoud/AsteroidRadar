package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.DateUtil.currentDate
import com.udacity.asteroidradar.Network.AsteriodRemoteDataSource
import com.udacity.asteroidradar.Network.NasaApi
import com.udacity.asteroidradar.Network.parseAsteriodResponseToList
import com.udacity.asteroidradar.Resource
import com.udacity.asteroidradar.database.AsteriodDAO
import com.udacity.asteroidradar.domain.DomainAsteriod
import com.udacity.asteroidradar.domain.asDatabaseModel
import com.udacity.asteroidradar.domain.asDomainModel
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
            //if list is not null then call fun asDatabaseModel (which convert from network object to db object in order to insert it)
            // and if this fun return is not null then insert it in the db ..
            //same as this asteriodDAO.insertAsteriod(* list.asDatabaseModel())
            list?.asDatabaseModel()?.let { asteriodDAO.insertAll(it) }

        }
    }

    fun getAllAsteriodList(): LiveData<List<DomainAsteriod>> =
        Transformations.map(
            asteriodDAO.getAllAsteriod(
                currentDate(),
                currentDate(DEFAULT_END_DATE_DAYS)
            )
        ) {
            it.asDomainModel()
        }


    fun getSavedAsteriodList(): LiveData<Resource<List<DomainAsteriod>>> =
        Transformations.map(asteriodDAO.getSavedAsteriod()) {
            Resource.success(it.asDomainModel())
        }


    fun getTodayAsteriodList(): LiveData<Resource<List<DomainAsteriod>>> =
        Transformations.map(
            asteriodDAO.getTodayAsteriod(
                currentDate()
            )
        ) {
            Resource.success(it.asDomainModel())

        }


    fun callAsteriodbyFilter(value: String): LiveData<Resource<List<DomainAsteriod>>> {
        when (value) {
            "week" -> {
                Log.i("yarab", "week")
                return getAsteriods()

            }
            "today" -> {
                Log.i("yarab", "today")
                return getTodayAsteriodList()
            }
            else ->  Log.i("yarab", "saved")
        }
        return getSavedAsteriodList()

    }


    fun getAsteriods() = performGetOperation(
        databaseQuery = {
            Transformations.map(
                asteriodDAO.getAllAsteriod(
                    currentDate(),
                    currentDate(DEFAULT_END_DATE_DAYS)
                )
            ) {
                it.asDomainModel()
            }
        },
        networkCall = { AsteriodRemoteDataSource().getAsteroids() },
        saveCallResult = {
            asteriodDAO.insertAll(
                parseAsteriodResponseToList(it.near_earth_objects).asDatabaseModel()
            )
        }
    )
}