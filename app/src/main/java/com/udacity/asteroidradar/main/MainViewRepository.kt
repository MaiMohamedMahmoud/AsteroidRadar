package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Constants.API_KEY
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
                NasaApi.nasaService.getAsteroids("2021-01-01", "2021-01-06", API_KEY)
            val list = parseAsteriodResponseToList(responseObj.near_earth_objects)
            asteriodDAO.insertAsteriod(* convertToDatabaseObject(list))
        }
    }

    fun getAllAsteriodList(): LiveData<List<DomainAsteriod>> {
        return convertDBObjectToDomain(asteriodDAO.getAllAsteriod().asLiveData())
    }

}