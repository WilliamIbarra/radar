package com.udacity.asteroidradar.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM Asteroid")
    fun getAsteroids(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroids: Asteroid)

}

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AsteroidsDB: RoomDatabase() {

    abstract val asteroidsDBDao: AsteroidsDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidsDB? = null

        fun getInstance(context: Context): AsteroidsDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDB::class.java,
                        "asteroids_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}