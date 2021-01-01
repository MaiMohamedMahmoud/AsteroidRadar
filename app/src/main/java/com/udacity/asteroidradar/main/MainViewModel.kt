package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    // The internal MutableLiveData String that stores the status of the most recent request
    private val _asteriodList = MutableLiveData<List<Asteroid>>()

    // The external immutable LiveData for the request status String
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteriodList

    init {
        getAllAsteroid()
    }

    private fun getAllAsteroid() {
        viewModelScope.launch {
            val responseObj = NasaApi.nasaService.getAsteroids("2015-09-07", "2015-09-10", API_KEY)
          //  asteroidList = responseObj.nearEarthObjects.keys
        }
    }
}