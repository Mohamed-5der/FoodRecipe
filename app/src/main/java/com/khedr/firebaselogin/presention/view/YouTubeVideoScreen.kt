package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import cafe.adriel.voyager.core.screen.Screen
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YouTubeVideoScreen ( val videoUrl : String):Screen {
    @Composable
    override fun Content() {
        Box (){
            Column (modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){

                YouTubePlayer(
                    videoUrl = videoUrl,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
            }
            Card(
                modifier = Modifier
                    .padding(start = 8.dp, top = 50.dp)
                    .size(40.dp)
                    .clickable { },
                backgroundColor = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Close",
                    modifier = Modifier.padding(8.dp)
                )
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