package com.udacity.asteroidradar

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CloseApproachData (
    @Json(name = "close_approach_date")
    val closeApproachDate: String
        ): Parcelable
