package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.DateUtil.currentDate
import com.udacity.asteroidradar.Network.NasaApi
import com.udacity.asteroidradar.Network.parseAsteriodResponseToList
import com.udacity.asteroidradar.Resource
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.DomainAsteriod
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(val mainViewRepository: MainViewRepository) : ViewModel() {


    private val _asteroidList = MutableLiveData<Resource<List<DomainAsteriod>>>()

    lateinit var list: LiveData<Resource<List<DomainAsteriod>>>

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    // The external immutable LiveData for the request status String
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    //status for navigation
    private val _statusNavigation = MutableLiveData<DomainAsteriod>()

    // The external immutable LiveData for the request status String
    val statusNavigation: LiveData<DomainAsteriod>
        get() = _statusNavigation

    init {
        getImageDay()
        _statusNavigation.value = null
    }

    var asteroidList = mainViewRepository.getAsteriods()

    fun filterAsteroid(value: String): LiveData<Resource<List<DomainAsteriod>>> {

        asteroidList = mainViewRepository.callAsteriodbyFilter(value)
        return asteroidList
    }

    private fun getImageDay() {
        try {
            viewModelScope.launch {
                val imageObj = NasaApi.nasaService.getPictureOfDay(API_KEY)
                Log.i("model", imageObj.toString())
                _pictureOfDay.value = imageObj.body()
            }
        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
        }
    }

    fun setNavigation(asteroid: DomainAsteriod) {
        _statusNavigation.value = asteroid
    }

    fun clearStatusNavigation() {
        _statusNavigation.value = null
    }
}