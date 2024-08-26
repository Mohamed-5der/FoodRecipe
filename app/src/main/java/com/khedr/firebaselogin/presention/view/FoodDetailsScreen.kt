package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.khedr.firebaselogin.R
import com.khedr.firebaselogin.presention.viewModel.DetailsViewModel

class FoodDetailsScreen(private val id: String) : Screen {

    @Composable
    override fun Content() {
        val detailsViewModel: DetailsViewModel = hiltViewModel()
        detailsViewModel.getMealDetails(id)
        FoodDetails(detailsViewModel)
    }

    @Composable
    fun FoodDetails(detailsViewModel: DetailsViewModel) {
        Box(modifier = Modifier.fillMaxSize()) {
            detailsViewModel.mealDetails.collectAsState().value?.let { mealDetails ->
                AsyncImage(
                    model = mealDetails.meals[0].strMealThumb,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(Alignment.TopStart),
                    contentScale = ContentScale.Crop
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(40.dp)
                            .clickable { /* Handle close action */ },
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Card(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(40.dp)
                            .clickable { /* Handle favorite action */ },
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = "Favorite",
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

                FoodDescription(mealDetails.meals[0].strYoutube?.split("=")?.get(1) ?: "")
            }
        }
    }

    @Composable
    fun FoodDescription(videoId: String) {
        Box {
            Card(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Meal Name", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Meal Description", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Ingredients", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Ingredient 1, Ingredient 2, Ingredient 3", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    YouTubeVideoScreen(videoId = videoId)
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun YouTubeVideoScreen(videoId: String) {
        val videoUrl = "https://www.youtube.com/embed/$videoId"
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadsImagesAutomatically = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        cacheMode = WebSettings.LOAD_NO_CACHE
                    }
                    webViewClient = WebViewClient()
                    loadUrl(videoUrl)
                }
            },
            update = { webView ->
                webView.loadUrl(videoUrl)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Set an appropriate height for the video player
        )
    }

}
