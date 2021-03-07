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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(val mainViewRepository: MainViewRepository) : ViewModel() {


    private val _asteroidList = MutableLiveData<Resource<List<DomainAsteriod>>>()

    lateinit var list: LiveData<Resource<List<DomainAsteriod>>>

    //status for navigation
    private val _statusNavigation = MutableLiveData<DomainAsteriod>()

    // The external immutable LiveData for the request status String
    val statusNavigation: LiveData<DomainAsteriod>
        get() = _statusNavigation

    init {
        _statusNavigation.value = null
    }

    var asteroidList = mainViewRepository.getAsteroids()

    fun filterAsteroid(value: String): LiveData<Resource<List<DomainAsteriod>>> {

        asteroidList = mainViewRepository.callAsteroidByFilter(value)
        return asteroidList
    }

     var pictureOfDay = mainViewRepository.getPictureOfDay()

    fun setNavigation(asteroid: DomainAsteriod) {
        _statusNavigation.value = asteroid
    }

    fun clearStatusNavigation() {
        _statusNavigation.value = null
    }
}