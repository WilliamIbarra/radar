package com.udacity.asteroidradar.data.database

import androidx.room.TypeConverter
import com.udacity.asteroidradar.CloseApproachData

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromList(list: List<CloseApproachData>): CloseApproachData {
            return list[0]
        }

        @TypeConverter
        @JvmStatic
        fun toList(data: CloseApproachData): List<CloseApproachData> {
            return arrayListOf(data)
        }
    }
}