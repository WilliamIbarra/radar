package com.udacity.asteroidradar

import com.squareup.moshi.Json

data class AsteroidsData(
    @Json(name = "near_earth_objects")
    val data: Map<String,List<Asteroid>>
)
