package com.udacity.asteroidradar

import android.view.View

object Constants {
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"

    //CONSTANTS FOR LOADING PROGRESSBAR
    const val LOADING =  View.VISIBLE
    const val SUCCESS = View.GONE
    const val ERROR = View.GONE

    // CONSTANTS FOR GET ASTEROIDS
    const val WEEK_ASTEROIDS = 0
    const val TODAY_ASTEROIDS = 1
    const val SAVED_ASTEROIDS = 2

    // Put your api key here
    const val API_KEY = "DZgj60AkvB1LEzqlIBGxR1VPsXrnRc8aETPx5pEK"
}