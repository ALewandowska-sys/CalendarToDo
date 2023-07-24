package com.app.calendartodo

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.app.calendartodo.databinding.ActivityMainBinding
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_calendar)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        setPreferenceLanguage()

        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }

    private fun setPreferenceLanguage() {
        val sharedPreferences = this.getSharedPreferences("NewSharedPreferences", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("language", "")
        if(name.isNullOrEmpty()){
            val default = "en"
            setAppLocale(this, default)
            addLanguage(default)
        }
    }

    private fun addLanguage(language :String) {
        val sharedPreferences = this.getSharedPreferences("NewSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("language", language)
        editor.apply()
    }

    fun changeLanguage(view: View?) {
        var language: String
        val sharedPreferences = this.getSharedPreferences("NewSharedPreferences", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("language", "")
        language = if (name.equals("en")) {
            "pl"
        } else {
            "en"
        }
        setAppLocale(this, language)
        addLanguage(language)
        recreate()
    }
    private fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        val updatedContext = context.createConfigurationContext(config)
        val updatedResources = updatedContext.resources
        val updatedConfiguration = updatedResources.configuration
        resources.updateConfiguration(updatedConfiguration, resources.displayMetrics)
    }
}