package edu.oregonstate.cs492.assignment4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastCityEntity (
    @PrimaryKey
    val name: String,
    val lastViewed: Long
)