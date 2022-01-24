package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.ERROR
import com.udacity.asteroidradar.Constants.LOADING
import com.udacity.asteroidradar.Constants.SUCCESS
import com.udacity.asteroidradar.Constants.WEEK_ASTEROIDS
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.data.database.AsteroidsDB
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.lang.Exception


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val asteroidsRepository = AsteroidsRepository(AsteroidsDB.getInstance(application))

    // Variable for the image of the day

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _asteroids = MutableLiveData<LiveData<List<Asteroid>>>()
    val asteroids: MutableLiveData<LiveData<List<Asteroid>>>
        get() = _asteroids


    //Status of the request
    private val _status = MutableLiveData<Int>()
        val status: LiveData<Int>
    get() = _status




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

    fun filterAsteroids(filter: Int) {
        when (filter) {
            0 -> {
               asteroids.postValue(getAsteroids())
            }
            1 -> {
               asteroids.postValue(getTodayAsteroids())
            }
            else -> {
               asteroids.postValue(asteroidsRepository.savedAsteroids)
            }
        }
    }


    // This function uses Api service to get the list of week asteroids
    private fun getAsteroids(): LiveData<List<Asteroid>> {

        _status.value = LOADING


        viewModelScope.launch {

            try {

                asteroidsRepository.refreshAsteroids()

                _status.value = SUCCESS
            } catch (ex: Exception) {

                Log.d("listError", ex.message ?: "undefined")
                _status.value = ERROR
            }

        }

        return asteroidsRepository.asteroids

    }


    // This function uses Api service to get today asteroids
    private fun getTodayAsteroids(): LiveData<List<Asteroid>> {
        _status.value = LOADING


        viewModelScope.launch {

            try {

                asteroidsRepository.getTodayAsteroids()
                _status.value = SUCCESS
            } catch (ex: Exception) {

                Log.d("listError", ex.message ?: "undefined")
                _status.value = ERROR
            }

        }

        return asteroidsRepository.todayAsteroids
    }




    init {
        getPictureOfDay()
        filterAsteroids(WEEK_ASTEROIDS)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }


    }
}




