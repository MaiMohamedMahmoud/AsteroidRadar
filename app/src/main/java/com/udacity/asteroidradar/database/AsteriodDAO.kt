package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteriodDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteriod(vararg asteroid: Entity.DBAsteroid)


    //here we didn't use suspend because we already use flow..
    @Query("SELECT * From Asteroid Order By close_approach_data ASC")
    suspend fun getSavedAsteriod(): List<Entity.DBAsteroid>

    //here we didn't use suspend because we already use flow..
    @Query("SELECT * From Asteroid where close_approach_data = :currentDate ")
    suspend fun getTodayAsteriod(currentDate: String): List<Entity.DBAsteroid>


    //here we didn't use suspend because we already use flow..
    @Query("SELECT * From Asteroid where close_approach_data between :startDate And :endDate Order By close_approach_data ASC")
    suspend fun getAllAsteriod(startDate: String, endDate: String): List<Entity.DBAsteroid>

    @Query("SELECT * From dayPicture")
    fun getPic(): Flow<Entity.DBPictureOfDay>

}