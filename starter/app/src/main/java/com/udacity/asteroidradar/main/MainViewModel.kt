package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsData
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.data.database.AsteroidsDB
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainViewModel(application: Application) : ViewModel() {

    private val asteroidsRepository = AsteroidsRepository(AsteroidsDB.getInstance(application))

    // Variable for the image of the day

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    // List of asteroids

    private val _listOfAsteroids = MutableLiveData<AsteroidsData>()
    val listOfAsteroids: LiveData<AsteroidsData>
        get() = _listOfAsteroids

    // Asteroids filtered
    private val _listOfAsteroidFiltered = MutableLiveData<List<Asteroid>>()
    val listOfAsteroidFiltered: LiveData<List<Asteroid>>
        get() = _listOfAsteroidFiltered


    // This method uses Api service to get the image of the day
    private fun getPictureOfDay() {

        viewModelScope.launch {
            try {
                val pictureOfDay = AsteroidApi.retrofitService.getPictureOfDay(API_KEY)
                _pictureOfDay.value = pictureOfDay
            } catch (ex: Exception) {
                _pictureOfDay.value = null
            }
        }
    }


    // This function uses Api service to get the list of week asteroids
    fun getAsteroids() {

        viewModelScope.launch {

            try {

                asteroidsRepository.refreshAsteroids()
                val asteroids = asteroidsRepository.asteroids

                val allAsteroids: ArrayList<Asteroid> = ArrayList()
                asteroids.value?.forEach { asteroid ->

                   allAsteroids.add(asteroid)

                }

                _listOfAsteroidFiltered.value = allAsteroids
                Log.d("dataAsteroid", asteroids.toString())
            } catch (ex: Exception) {
                _listOfAsteroids.value = null
                _listOfAsteroidFiltered.value = null
                Log.d("listError", ex.message ?: "undefined")
            }

        }


    }

    // This function uses Api service to get the today asteroids
    fun getTodayAsteroids() {


        viewModelScope.launch {

            try {


                asteroidsRepository.refreshAsteroids()
                val asteroids = asteroidsRepository.asteroids

                _listOfAsteroidFiltered.value = asteroids.value
                Log.d("dataAsteroid", asteroids.toString())
            } catch (ex: Exception) {
                _listOfAsteroids.value = null
                _listOfAsteroidFiltered.value = null
                Log.d("listError", ex.message ?: "undefined")
            }

        }


    }

    init {
        getPictureOfDay()
        getAsteroids()
    }
}