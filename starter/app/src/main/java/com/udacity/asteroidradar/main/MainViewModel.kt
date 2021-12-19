package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsData
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

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
            AsteroidApi.retrofitService.getPictureOfDay().enqueue(object : Callback<PictureOfDay> {
                override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                    _pictureOfDay.value = null
                }

                override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                    _pictureOfDay.value = response.body()
                }
            })
        }


    }


    // This function uses Api service to get the list of asteroids
    private fun getAsteroids() {

        viewModelScope.launch {
            AsteroidApi.retrofitService.getAsteroids().enqueue(object : Callback<AsteroidsData> {
                override fun onFailure(call: Call<AsteroidsData>, t: Throwable) {
                    _listOfAsteroids.value = null

                    Log.d("listError", t.message ?: "undefined")
                }

                override fun onResponse(
                    call: Call<AsteroidsData>,
                    response: Response<AsteroidsData>
                ) {
                    _listOfAsteroids.value = response.body()

                    response.body()?.data?.let { data ->
                        _listOfAsteroidFiltered.value = data["2021-12-03"]

                        Log.d("dataAsteroid", data["2021-12-03"].toString())
                    }
                }
            })
        }


    }

    init {
        getPictureOfDay()
        getAsteroids()
    }
}