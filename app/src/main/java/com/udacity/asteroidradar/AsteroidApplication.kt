package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.worker.RefreshDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayInit()

    }

    private fun delayInit() {
        applicationScope.launch {
            setupWorker()
        }
    }

    private fun setupWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            //Marshmello or above
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        //here we need to tell our work manager to run our job
        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshDataWork>(1, TimeUnit.DAYS).setConstraints(constraints).build()
        //to schedule the work we need to use enqueue messege in WorkManager
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWork.work_name,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}