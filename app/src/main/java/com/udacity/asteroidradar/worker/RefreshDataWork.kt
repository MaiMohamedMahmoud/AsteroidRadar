package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.DatabaseAsteriod.Companion.getDatabase
import com.udacity.asteroidradar.main.MainViewRepository
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val mainRepo = MainViewRepository(database.asteriodDao())
        return try {
            mainRepo.refreshDatabasefun()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }

    }

    companion object {
        const val work_name = "RefreshWorker"
    }

}