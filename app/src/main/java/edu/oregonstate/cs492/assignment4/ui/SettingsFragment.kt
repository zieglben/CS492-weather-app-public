package edu.oregonstate.cs492.assignment4.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import edu.oregonstate.cs492.assignment4.R
import edu.oregonstate.cs492.assignment4.data.ForecastCityEntity

/**
 * This fragment represents a settings screen for the app.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel: ForecastCityViewModel by viewModels()
    private lateinit var prefs: SharedPreferences
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefs.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "city"){
                Log.d("SettingsFragment", "City changed to " +
                        "${prefs.getString("city", "")}")
                val city = ForecastCityEntity(
                    prefs.getString("city", "").toString(),
                    System.currentTimeMillis())
                viewModel.addForecastCity(city)
            }
        }
    }
}