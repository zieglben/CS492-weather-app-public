package edu.oregonstate.cs492.assignment4.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastCityDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: ForecastCityEntity)

    @Query("SELECT * FROM ForecastCityEntity")
    fun getAllForecastCities() : Flow<List<ForecastCityEntity>>

    @Query("SELECT * FROM ForecastCityEntity WHERE name = :name LIMIT 1")
    fun getForecastCityByName(name: String) : Flow<ForecastCityEntity?>
}