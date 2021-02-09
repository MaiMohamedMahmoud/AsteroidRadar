package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.domain.UrlResponse
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import timber.log.Timber

fun <T> performDataBaseOperation(
    databaseQuery: () -> LiveData<T>
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        //emit(Resource.loading())
        Log.i("yarab", "inside strategy perform")
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

    }

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            Log.i("yarab", "inside strategy")
            Log.i("yarab", "" + responseStatus.data)
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            Timber.i("inside strategy fail")
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }