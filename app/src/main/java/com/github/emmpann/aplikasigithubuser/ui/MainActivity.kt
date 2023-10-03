package com.github.emmpann.aplikasigithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.emmpann.aplikasigithubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityMainBinding.root)
    }
}