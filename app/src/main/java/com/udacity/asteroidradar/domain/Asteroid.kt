package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(@Json(name = "asteroid_id")
                    val id: Long,
                    @Json(name = "asteroid_codename")
                    val codename: String,
                    @Json(name = "asteroid_closeApproachDate")
                    val closeApproachDate: String,
                    @Json(name = "asteroid_absoluteMagnitude")
                    val absoluteMagnitude: Double,
                    @Json(name = "asteroid_estimatedDiameter")
                    val estimatedDiameter: Double,
                    @Json(name = "asteroid_relativeVelocity")
                    val relativeVelocity: Double,
                    @Json(name = "asteroid_distanceFromEarth")
                    val distanceFromEarth: Double,
                    @Json(name = "asteroid_isPotentiallyHazardous")
                    val isPotentiallyHazardous: Boolean) : Parcelable