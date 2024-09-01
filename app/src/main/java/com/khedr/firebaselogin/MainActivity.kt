package com.khedr.firebaselogin

import FireBaseViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel

import cafe.adriel.voyager.navigator.Navigator
import com.khedr.firebaselogin.presention.view.HomeScreen

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var fireBaseViewModel : FireBaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                fireBaseViewModel = hiltViewModel()
               // Navigator(screen = SplashScreen(fireBaseViewModel))
                Navigator(screen = HomeScreen())
            }

    }
}
