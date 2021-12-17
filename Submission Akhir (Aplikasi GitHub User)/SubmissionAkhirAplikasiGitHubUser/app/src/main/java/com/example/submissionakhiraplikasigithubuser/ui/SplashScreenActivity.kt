package com.example.submissionakhiraplikasigithubuser.ui
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionakhiraplikasigithubuser.R
import com.example.submissionakhiraplikasigithubuser.helper.SettingPreferences
import com.example.submissionakhiraplikasigithubuser.viewmodel.ThemeViewModel
import com.example.submissionakhiraplikasigithubuser.viewmodel.ThemeViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    var delayTime : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        changeTheme()

        Handler(Looper.getMainLooper()).postDelayed({
            val splashScreen = Intent(this, MainActivity::class.java)
            startActivity(splashScreen)
            finish()
        }, delayTime)
    }

    private fun changeTheme(){
        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })


    }
}