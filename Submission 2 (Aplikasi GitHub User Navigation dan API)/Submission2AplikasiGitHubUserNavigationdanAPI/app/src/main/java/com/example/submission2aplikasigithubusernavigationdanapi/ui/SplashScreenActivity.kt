package com.example.submission2aplikasigithubusernavigationdanapi.ui
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.submission2aplikasigithubusernavigationdanapi.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    var delayTime : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val splashScreen = Intent(this, MainActivity::class.java)
            startActivity(splashScreen)
            finish()
        }, delayTime)
    }
}