package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    @Json(name = "asteroid_id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "close_approach_data")
    val close_approach_data: List<CloseApproachDate>,
    @Json(name = "asteroid_distanceFromEarth")
    val distanceFromEarth: Double,
    @Json(name = "absolute_magnitude_h")
    val absolute_magnitude_h: Double,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
) : Parcelable


@Parcelize
data class CloseApproachDate(
    @Json(name = "close_approach_date")
    val close_approach_date: String,
    @Json(name = "relative_velocity")
    val relative_velocity: RelativeVelocity,
    @Json(name = "miss_distance")
    val miss_distance: MissDistance
) : Parcelable

@Parcelize
data class MissDistance(
    val astronomical: String
) : Parcelable

@Parcelize
data class RelativeVelocity(
    @Json(name = "kilometers_per_second")
    val kilometers_per_second: String
) : Parcelable

@Parcelize
data class EstimatedDiameter(
    @Json(name = "kilometers")
    val kilometers: Feet
) : Parcelable

@Parcelize
data class Feet(
    @Json(name = "estimated_diameter_max")
    val estimated_diameter_max: Double
) : Parcelable
