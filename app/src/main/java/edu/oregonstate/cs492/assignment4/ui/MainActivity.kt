package edu.oregonstate.cs492.assignment4.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import edu.oregonstate.cs492.assignment4.R
import edu.oregonstate.cs492.assignment4.data.ForecastCityEntity

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var prefs: SharedPreferences
    private val viewModel: ForecastCityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)

        val appBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        setSupportActionBar(appBar)
        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        //SET DEFAULT DATABASE WITH CORVALLIS,OR,US OR WITH DEFAULT VALUE
        val defaultCity = ForecastCityEntity(
            prefs.getString("city", "Corvallis,OR,US").toString(),
            System.currentTimeMillis()
        )
        viewModel.addForecastCity(defaultCity)

//        prefs.registerOnSharedPreferenceChangeListener { _, key ->
//            if (key == "city"){
//                Log.d("MainActivity", "City changed to " +
//                        "${prefs.getString("city", "")}")
//                val city = ForecastCityEntity(
//                    prefs.getString("city", "").toString(),
//                    System.currentTimeMillis())
//                viewModel.addForecastCity(city)
//            }
//        }

        viewModel.forecastCities.observe(this) {
            val navView: NavigationView = findViewById(R.id.nav_view)
            val subMenu = navView.menu.findItem(R.id.cities).subMenu
            subMenu?.clear()
            for (city in it.reversed()){
                subMenu?.add(city.name)?.setOnMenuItemClickListener {
                    Log.d("Main Activity", "Loaded city is: ${city.name}")
                    prefs.edit().putString("city", city.name).apply()
                    val updatedCity = ForecastCityEntity(
                        prefs.getString("city", "").toString(),
                        System.currentTimeMillis())
                    viewModel.addForecastCity(updatedCity)
                    navController.navigate(R.id.current_weather)
                    drawerLayout.closeDrawers() //Close drawers
                    true
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}