package edu.oregonstate.cs492.assignment4.data

class ForecastCityRepository(private val dao: ForecastCityDao) {
    suspend fun insertForecastCity(city: ForecastCityEntity) = dao.insert(city)
    fun getAllCities() = dao.getAllForecastCities()
    fun getCityByName(name: String) = dao.getForecastCityByName(name)
}