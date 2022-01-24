package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.data.database.AsteroidsDB
import com.udacity.asteroidradar.data.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AsteroidsRepository(private val database: AsteroidsDB) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidsDBDao.getAsteroids()) {
        it.asDomainModel()
    }

    val todayAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidsDBDao.getTodayAsteroids(getActualDate())) {
        it.asDomainModel()
    }

    val savedAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidsDBDao.getAsteroids()) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO){
            val asteroids = AsteroidApi.retrofitService.getWeekAsteroids(getActualDate(), API_KEY)

            val allAsteroids: ArrayList<Asteroid> = ArrayList()
            asteroids.data.values.forEach { asteroidsList ->

                asteroidsList.forEach { asteroid ->
                    allAsteroids.add(asteroid)
                }

            }

            database.asteroidsDBDao.insertAllAsteroids(allAsteroids.asDatabaseModel())
        }
    }

    suspend fun getTodayAsteroids(){
        withContext(Dispatchers.IO){
            val asteroids = AsteroidApi.retrofitService.getTodayAsteroids(getActualDate(), getActualDate(), API_KEY)

            val allAsteroids: ArrayList<Asteroid> = ArrayList()
            asteroids.data.values.forEach { asteroidsList ->

                asteroidsList.forEach { asteroid ->
                    allAsteroids.add(asteroid)
                }

            }

            database.asteroidsDBDao.insertAllAsteroids(allAsteroids.asDatabaseModel())
        }
    }


    private fun getActualDate(): String {
        val date = Calendar.getInstance().timeInMillis
        val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        Log.d("dateS", formatter.format(date))
        return formatter.format(date)
    }
}