package com.udacity.asteroidradar.domain

import com.squareup.moshi.Json

data class Response(
    @Json(name = "links")
    val links: links,
    @Json(name = "element_count")
    val element_count: Long,
    @Json(name = "near_earth_objects")
    val near_earth_objects: Map<String, List<Asteroid>>
)

data class links(
    val next: String,
    val prev: String,
    val self: String
)