package com.udacity.asteroidradar.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.database.Entity

fun List<Entity.DBAsteroid>.asDomainModel(): List<DomainAsteriod> {
    return map {
        DomainAsteriod(
            id = it.id,
            name = it.name,
            close_approach_data = it.close_approach_data,
            relative_velocity = it.relative_velocity,
            distanceFromEarth = it.distanceFromEarth,
            absolute_magnitude_h = it.absolute_magnitude_h,
            estimated_diameter = it.estimated_diameter,
            is_potentially_hazardous_asteroid = it.is_potentially_hazardous_asteroid
        )
    }
}

// Convert Network results to database objects
fun List<Asteroid>.asDatabaseModel(): List<Entity.DBAsteroid> {
    return map {
        Entity.DBAsteroid(
            id = it.id,
            name = it.name,
            close_approach_data = it.close_approach_data.get(0).close_approach_date,
            distanceFromEarth = it.close_approach_data.get(0).miss_distance.astronomical,
            relative_velocity = it.close_approach_data.get(0).relative_velocity.kilometers_per_second,
            absolute_magnitude_h = it.absolute_magnitude_h,
            estimated_diameter = it.estimated_diameter.kilometers.estimated_diameter_max,
            is_potentially_hazardous_asteroid = it.is_potentially_hazardous_asteroid
        )
    }
}

//    fun convertToDatabaseObject(list: List<Asteroid>): Array<Entity.DBAsteroid> {
//        return list.map {
//            Entity.DBAsteroid(
//                id = it.id,
//                name = it.name,
//                close_approach_data = it.close_approach_data.get(0).close_approach_date,
//                distanceFromEarth = it.close_approach_data.get(0).miss_distance.astronomical,
//                relative_velocity = it.close_approach_data.get(0).relative_velocity.kilometers_per_second,
//                absolute_magnitude_h = it.absolute_magnitude_h,
//                estimated_diameter = it.estimated_diameter.kilometers.estimated_diameter_max,
//                is_potentially_hazardous_asteroid = it.is_potentially_hazardous_asteroid
//            )
//        }.toTypedArray()
//    }
