package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YouTubeVideoScreen ( val videoUrl : String):Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(Color.White)
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            containerColor = Color.White,
            topBar = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    androidx.compose.material.TopAppBar(modifier = Modifier
                        .background(Color.White)
                        .height(80.dp),
                        backgroundColor = Color.White,
                        navigationIcon = {
                            IconButton(
                                onClick = { navigator.pop() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Spacer(modifier = Modifier.weight(0.4f))
                            androidx.compose.material.Text(
                                "Recipe Video", modifier = Modifier
                                    .padding(top = 20.dp),
                                fontFamily = poppins,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.weight(0.8f))
                        })
                }

            }
        ){
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    YouTubePlayer(
                        videoUrl = videoUrl,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }
            }
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun YouTubePlayer(
        videoUrl: String,
        lifecycleOwner: LifecycleOwner
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            factory = {
                YouTubePlayerView(context = it).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoUrl, 0f)
                        }
                    })
                }
            }
        )
    }
}