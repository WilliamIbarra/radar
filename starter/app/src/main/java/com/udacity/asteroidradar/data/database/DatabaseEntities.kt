package com.udacity.asteroidradar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.*

@Entity
data class DatabaseAsteroids constructor(
    @PrimaryKey
    val id: Long,
    val codename: String?,
    val closeApproachDate: String?,
    val relativeVelocity: Float?,
    val missDistance: Float?,
    val absoluteMagnitude: Double?,
    val minDiameter: Float?,
    val maxDiameter: Float?,
    val isPotentiallyHazardous: Boolean?
)

fun List<DatabaseAsteroids>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachData =
               listOf( CloseApproachData(
                    closeApproachDate = it.closeApproachDate,
                    relativeVelocity = RelativeVelocity(
                        kilometersPerSecond = it.relativeVelocity
                    ),
                    distanceFromEarth = MissDistance(
                        astronomical = it.missDistance
                    )
                ))
            ,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = Diameter(
                kilometers = Kilometers(
                    minDiameter = it.minDiameter,
                    maxDiameter = it.maxDiameter
                )
            ),
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
