package com.khedr.firebaselogin

import FireBaseViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel

import cafe.adriel.voyager.navigator.Navigator
import com.khedr.firebaselogin.presention.view.HomeScreen
import com.khedr.firebaselogin.presention.view.LoginScreen
import com.khedr.firebaselogin.presention.view.SplashScreen
import com.khedr.firebaselogin.presention.viewModel.HomeViewModel
import com.khedr.firebaselogin.ui.theme.FireBaseloginTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                val fireBaseViewModel = FireBaseViewModel(application)
                val homeViewModel : HomeViewModel = hiltViewModel()
                Navigator(screen = HomeScreen(application))
            }

    }
}
