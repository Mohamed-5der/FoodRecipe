package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.khedr.firebaselogin.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen (val firebaseViewModel: FireBaseViewModel): Screen {
    @Composable
    override fun Content() {
        Splash(firebaseViewModel)
    }
    @Composable
    fun Splash(firebaseViewModel: FireBaseViewModel){
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = true) {
            delay(2000)
            navigator.push(LoginScreen(firebaseViewModel))
        }
        Box(modifier =Modifier.fillMaxSize()    ,
            contentAlignment = Alignment.Center){
            Image(painter = painterResource(id = R.drawable.splash_image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth)


        }


    }
}