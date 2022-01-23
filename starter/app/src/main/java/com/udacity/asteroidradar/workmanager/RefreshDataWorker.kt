package com.udacity.asteroidradar.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.database.AsteroidsDB
import com.udacity.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
CoroutineWorker(appContext, params){
    override suspend fun doWork(): Result {
        val database = AsteroidsDB.getInstance(applicationContext)
        val repository = AsteroidsRepository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}