package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteriodDAO {

    @Insert()
    suspend fun insertAsteriod(asteroid: Entity.DBAsteroid)

    @Insert()
    suspend fun insertPicture(pictureOfDay: PictureOfDay)

    //here we didn't use suspend because we already use flow..
    @Query("SELECT * From Asteroid")
    fun getAllAsteriod(): Flow<List<Entity.DBAsteroid>>

    @Query("SELECT * From dayPicture")
    fun getPic():Flow<Entity.DBPictureOfDay>

}