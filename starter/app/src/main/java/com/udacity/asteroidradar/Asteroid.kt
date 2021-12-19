package com.udacity.asteroidradar

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: Long,
    @Json(name = "name")
    val codename: String,
    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachData>?,
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: Diameter,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
) : Parcelable

@Parcelize
data class Diameter(
    @Json(name = "kilometers")
    val kilometers: Kilometers
) : Parcelable

@Parcelize
data class Kilometers(
    @Json(name = "estimated_diameter_min")
    val minDiameter: Float,
    @Json(name = "estimated_diameter_max")
    val maxDiameter: Float
) : Parcelable
