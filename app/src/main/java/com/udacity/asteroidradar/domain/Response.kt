package com.udacity.asteroidradar.domain

import com.squareup.moshi.Json

data class Response(
    @Json(name = "element_count")
    val elementCount: Long,

    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<Asteroid>>
)