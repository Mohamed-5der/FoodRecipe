package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.khedr.firebaselogin.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : Screen {
    lateinit var firebaseViewModel: FireBaseViewModel
    @Composable
    override fun Content() {
        firebaseViewModel = hiltViewModel()
        Splash(firebaseViewModel)
    }
    @Composable
    fun Splash(firebaseViewModel: FireBaseViewModel){
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = true) {
            delay(2000)
            try {
                if (firebaseViewModel.currentUser.value?.uid != null && firebaseViewModel.currentUser.value?.email != null) {
                    navigator.push(HomeScreen())
                }else{
                    navigator.push(LoginScreen(firebaseViewModel))
                }
            }catch (e:Exception){
                Log.d("TAG", "Splash: ${e.message}")

            }

        }
        Box(modifier =Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center){
            Image(painter = painterResource(id = R.drawable.logo_recipe),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth)
        }


    }
}