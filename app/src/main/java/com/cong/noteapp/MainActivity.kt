package com.cong.noteapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.cong.noteapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.navController

        applySavedLanguage()
        applyDarkMode()

    }

    fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun setLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun saveLanguage(language: String) {

        changeLanguage(language)

        val editor = getSharedPreferences("changeMode", Context.MODE_PRIVATE)
        editor.edit().putString("language", language).apply()

        recreate()

    }

    private fun applySavedLanguage() {
        val editor = getSharedPreferences("changeMode", Context.MODE_PRIVATE)
        val language = editor.getString("language", "")

        changeLanguage(language.toString())
    }

    private fun applyDarkMode() {
        val editor = getSharedPreferences("changeMode", Context.MODE_PRIVATE)
        val isDarkMode = editor.getBoolean("darkMode", false)

        mainViewModel.checkedDarkMode.postValue(isDarkMode)

        if (isDarkMode) {
            setDarkMode()
        } else {
            setLightMode()
        }

    }

    private fun changeLanguage(language: String) {
        val locale = Locale(language)

        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}