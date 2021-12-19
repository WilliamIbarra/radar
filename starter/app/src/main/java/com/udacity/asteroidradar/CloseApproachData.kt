package com.udacity.asteroidradar

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CloseApproachData (
    @Json(name = "close_approach_date")
    val closeApproachDate: String,
    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity,
    @Json(name = "miss_distance")
    val distanceFromEarth: MissDistance
        ): Parcelable

@Parcelize
data class RelativeVelocity(
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: Float
): Parcelable

@Parcelize
data class MissDistance(
    @Json(name = "astronomical")
    val astronomical: Float
): Parcelable
