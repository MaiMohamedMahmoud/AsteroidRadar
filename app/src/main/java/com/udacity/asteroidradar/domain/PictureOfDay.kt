package com.udacity.asteroidradar.domain

import com.squareup.moshi.Json

data class PictureOfDay(
    @Json(name = "media_type")
    val media_type: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String
)