package com.udacity.asteroidradar.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainAsteriod(
    val id: Long,
    val name: String,
    val close_approach_data: String,
    val relative_velocity:Double,
    val distanceFromEarth: Double,
    val absolute_magnitude_h: Double,
    val estimated_diameter: Double,
    val is_potentially_hazardous_asteroid: Boolean
):Parcelable