package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Network.NasaApi
import com.udacity.asteroidradar.Network.parseAsteriodResponseToList
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {


    // The internal MutableLiveData String that stores the status of the most recent request
    private val _asteriodList = MutableLiveData<List<Asteroid>>()

    // The external immutable LiveData for the request status String
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteriodList

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    // The external immutable LiveData for the request status String
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    //status for navigation
    private val _statusNavigation = MutableLiveData<Asteroid>()

    // The external immutable LiveData for the request status String
    val statusNavigation: LiveData<Asteroid>
        get() = _statusNavigation

    init {
        getAllAsteroid()
        getImageDay()
        _statusNavigation.value = null
    }


    private fun getAllAsteroid() {
        try {
            viewModelScope.launch {
                val responseObj =
                    NasaApi.nasaService.getAsteroids("2021-01-01", "2021-01-07", API_KEY)
                Log.i("model", responseObj.toString())
                _asteriodList.value = parseAsteriodResponseToList(responseObj.near_earth_objects)
            }
        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
        }
    }

    private fun getImageDay() {
        try {
            viewModelScope.launch {

                val imageObj = NasaApi.nasaService.getPictureOfDay(API_KEY)
                Log.i("model", imageObj.toString())
                _pictureOfDay.value = imageObj


            }
        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
        }
    }

    fun setNavigation(asteriod: Asteroid) {
        _statusNavigation.value = asteriod
    }

    fun clearStatusNavigation() {
        _statusNavigation.value = null
    }
}