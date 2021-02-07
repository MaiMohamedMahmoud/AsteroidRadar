package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteriods: List<Entity.DBAsteroid>)

    @Query("SELECT * From Asteroid Order By close_approach_data ASC")
    fun getSavedAsteriod(): LiveData<List<Entity.DBAsteroid>>

    @Query("SELECT * From Asteroid where close_approach_data = :currentDate ")
    fun getTodayAsteriod(currentDate: String): LiveData<List<Entity.DBAsteroid>>


    @Query("SELECT * From Asteroid where close_approach_data between :startDate And :endDate Order By close_approach_data ASC")
    fun getAllAsteriod(
        startDate: String,
        endDate: String
    ): LiveData<List<Entity.DBAsteroid>>

    @Query("SELECT * From dayPicture")
    fun getPic(): Flow<Entity.DBPictureOfDay>

}