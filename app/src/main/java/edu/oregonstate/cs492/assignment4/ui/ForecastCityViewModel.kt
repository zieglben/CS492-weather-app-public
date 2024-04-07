package edu.oregonstate.cs492.assignment4.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.assignment4.data.AppDatabase
import edu.oregonstate.cs492.assignment4.data.ForecastCityEntity
import edu.oregonstate.cs492.assignment4.data.ForecastCityRepository
import kotlinx.coroutines.launch

class ForecastCityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ForecastCityRepository(
        AppDatabase.getInstance(application).forecastCityDao()
    )

    val forecastCities = repository.getAllCities().asLiveData()

    fun addForecastCity(city: ForecastCityEntity){
        viewModelScope.launch {
            repository.insertForecastCity(city)
        }
    }

    fun getCityByName(name: String) =
        repository.getCityByName(name).asLiveData()
}