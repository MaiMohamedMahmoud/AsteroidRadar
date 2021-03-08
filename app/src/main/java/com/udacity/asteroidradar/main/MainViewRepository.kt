package com.udacity.asteroidradar.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.observe
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
import com.udacity.asteroidradar.performDataBaseOperation
import com.udacity.asteroidradar.performGetOperation
import com.udacity.asteroidradar.performRefreshDataBaseOperation
import kotlinx.coroutines.Dispatchers
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


    //get Week Asteroid from db....
    fun getWeeklyAsteroid(): LiveData<Resource<List<DomainAsteriod>>> {
        Log.i("yarab", "inside All")

        return Transformations.map(
            asteriodDAO.getAllAsteriod(
                currentDate(),
                currentDate(DEFAULT_END_DATE_DAYS)
            )
        ) {

            Log.i("yarab", "" + it.asDomainModel().size)
            Resource.success(it.asDomainModel())
        }
    }


    fun getSavedAsteriodList(): LiveData<Resource<List<DomainAsteriod>>> =
        Transformations.map(asteriodDAO.getSavedAsteriod()) {
            Resource.success(it.asDomainModel())
        }

    fun getTodayAsteroid(): LiveData<Resource<List<DomainAsteriod>>> =
        Transformations.map(
            asteriodDAO.getTodayAsteriod(
                currentDate()
            )
        ) {
            Log.i("yarab", "inside tody fun")
            Resource.success(it.asDomainModel())

        }


    fun callAsteroidByFilter(value: String): LiveData<Resource<List<DomainAsteriod>>> {
        when (value) {
            "week" -> {
                Log.i("yarab", "week")
                return getWeeklyAsteroid()
            }
            "today" -> {
                Log.i("yarab", "today")
                return getTodayAsteroid()
            }
            else -> return getSavedAsteriodList()
        }
    }


    fun getTodayList() = performDataBaseOperation(
        databaseQuery = {
            Transformations.map(
                asteriodDAO.getTodayAsteriod(
                    currentDate()
                )
            ) {
                Log.i("yarab", "inside tody fun")
                it.asDomainModel()

            }
        }
    )

    fun getAsteroids() = performGetOperation(
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

    suspend fun refreshDatabasefun() = performRefreshDataBaseOperation(
        networkCall = { AsteriodRemoteDataSource().getAsteroids() },
        saveCallResult = {
            asteriodDAO.insertAll(
                parseAsteriodResponseToList(it.near_earth_objects).asDatabaseModel()
            )
        }
    )

    fun getPictureOfDay() = performGetOperation(
        databaseQuery = {
            Transformations.map(
                asteriodDAO.getPic(currentDate())
            ) {
                it?.asDomainModel()
            }
        },
        networkCall = { AsteriodRemoteDataSource().getPictureOfDay() },
        saveCallResult = { asteriodDAO.insertPic(it.asDatabaseModel()) }
    )


}