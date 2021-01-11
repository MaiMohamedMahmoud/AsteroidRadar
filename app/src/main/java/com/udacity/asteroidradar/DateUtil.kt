package com.udacity.asteroidradar

import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun currentDate(nextDay: Int = 0): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, nextDay)
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }
}