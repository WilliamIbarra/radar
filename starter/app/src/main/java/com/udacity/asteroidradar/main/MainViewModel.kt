package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    // Variable for the image of the day

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    // This method uses Api service to get the image of the day
   private fun getPictureOfDay() {

       AsteroidApi.retrofitService.getPictureOfDay().enqueue(object : Callback<PictureOfDay> {
           override fun onFailure(call: Call<PictureOfDay>, t: Throwable){
                _pictureOfDay.value = null
           }

           override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
            _pictureOfDay.value = response.body()
           }
       })
   }

    init {
        getPictureOfDay()
    }
}