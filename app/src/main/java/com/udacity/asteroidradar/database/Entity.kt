package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.CloseApproachDate
import com.udacity.asteroidradar.domain.EstimatedDiameter

class Entity {

    @Entity(tableName = "Asteroid")
    data class DBAsteroid(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val name: String,
        val close_approach_data: String,
        val distanceFromEarth: Double,
        val absolute_magnitude_h: Double,
        val estimated_diameter: String,
        val is_potentially_hazardous_asteroid: Boolean
    )


    @Entity(tableName = "DayPicture")
    data class DBPictureOfDay(
        val media_type: String,
        val title: String,
        val url: String
    )
}