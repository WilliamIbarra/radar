package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroidradar.data.database.DatabaseAsteroids
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Asteroid (
    @PrimaryKey
    val id: Long,
    @Json(name = "name")
    val codename: String?,
    @Json(name = "close_approach_data")
    @Ignore val closeApproachData: List<CloseApproachData>?,
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double?,
    @Json(name = "estimated_diameter")
    @Embedded val estimatedDiameter: Diameter?,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean?
) : Parcelable {
    constructor(
        id: Long,
        codename: String?,
        absoluteMagnitude: Double?,
        estimatedDiameter: Diameter?,
        isPotentiallyHazardous: Boolean?
    ) : this(
        id,
        codename,
        emptyList(),
        absoluteMagnitude,
        estimatedDiameter,
        isPotentiallyHazardous
    )
}
@Entity
@Parcelize
data class Diameter(
    @Json(name = "kilometers")
    @Embedded val kilometers: Kilometers
) : Parcelable

@Parcelize
data class Kilometers(
    @Json(name = "estimated_diameter_min")
    val minDiameter: Float?,
    @Json(name = "estimated_diameter_max")
    val maxDiameter: Float?
) : Parcelable

fun ArrayList<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroids> {
    return this.map{
        DatabaseAsteroids(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachData?.get(0)?.closeApproachDate ?: "",
            relativeVelocity = it.closeApproachData?.get(0)?.relativeVelocity?.kilometersPerSecond ?: 0.0F,
            missDistance = it.closeApproachData?.get(0)?.distanceFromEarth?.astronomical ?: 0.0F,
            absoluteMagnitude = it.absoluteMagnitude,
            minDiameter = it.estimatedDiameter?.kilometers?.minDiameter,
            maxDiameter = it.estimatedDiameter?.kilometers?.maxDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}
