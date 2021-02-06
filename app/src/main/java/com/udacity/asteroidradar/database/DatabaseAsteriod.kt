package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Entity.DBAsteroid::class, Entity.DBPictureOfDay::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseAsteriod : RoomDatabase() {
    /**
     * first we need to get handel of the function inside our TaskDao interface
     * so define this fun
     * abstract because we doesn't want to make any implementation.
     */
    abstract fun asteriodDao(): AsteriodDAO

    /**
     * we need to have only one instance from the database(Singleton)
     * so make a companion object or create an object class..
     */


    companion object {
        @Volatile
        private var INSTANCE: DatabaseAsteriod? = null

        fun getDatabase(
            context: Context
        ): DatabaseAsteriod {

            /**
             * Wrapping the code in synchronized(){}
             * Means, Only one thread can access the code at a time
             * So we make sure we have only one instace of DB
             * if the INSTANCE is not null, then return it,
             * if it is, then create the database
             */

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseAsteriod::class.java,
                    "Asteriod_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

