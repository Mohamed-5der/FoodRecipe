package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.khedr.firebaselogin.R
import com.khedr.firebaselogin.presentation.viewModel.HomeViewModel
import kotlin.random.Random

class HomeScreenContent : Screen {
    lateinit var homeViewModel : HomeViewModel
    lateinit var navigator : Navigator

    @Composable
    override fun Content() {
            homeViewModel  = hiltViewModel()
            navigator=LocalNavigator.currentOrThrow
            val isLoading by homeViewModel.isLoading.collectAsState()

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.Black)
                }
            } else {
                HomeContent(homeViewModel = homeViewModel, navigator = navigator, application = Application())
            }

    }
    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    fun HomeContent(homeViewModel: HomeViewModel, navigator: Navigator,application: Application) {
        val name = remember { mutableStateOf("Mohamed Khedr") }
        Column(
            modifier = Modifier
                .padding(start = 24.dp, top = 12.dp, end = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFFFF7622)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Good Morning",
                    fontFamily = senFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.darkBlue)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(id = R.color.orange)
                )
            }

            Text(
                text = name.value,
                fontFamily = senFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = colorResource(id = R.color.darkBlue)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.featured),
                fontFamily = senFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            FoodCategoriesCard(application)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.dish_of_the_day),
                fontFamily = senFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            val dishOfDay =homeViewModel.dishOfDay.collectAsState().value?.get(0)?: null

            Card(
                onClick = {
                    navigator.push(FoodDetailsScreen(dishOfDay?.idMeal?:""))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Box {
                    AsyncImage(
                        model = dishOfDay?.strMealThumb ,
                        contentDescription = "Image description",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(color = colorResource(id = R.color.white)),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopEnd,
                        placeholder = rememberAsyncImagePainter("https://example.com/placeholder.png"),
                        error = rememberAsyncImagePainter("https://example.com/error.png")
                    )
                    Column (  modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)){

                        Image(
                            painterResource(id = R.drawable.arrow_right),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.End)
                        )
                        Row(
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        ) {
                            dishOfDay?.strMeal?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(),
                                    fontSize = 28.sp,
                                    fontFamily = senFontFamily,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colorResource(id = R.color.white),
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(0f, 4f),
                                            blurRadius = 4f
                                        )
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))

                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.meals),
                    fontFamily = senFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(id = R.string.sea_all),
                    fontFamily = senFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFFFF7622),
                    modifier = Modifier.clickable {
                        Toast.makeText(application, "Sea All", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            RecipeCard()

        }
    }

    @Composable
    fun RecipeCard(
        modifier: Modifier = Modifier
    ) {
        val meals = homeViewModel.meals.collectAsState()

        LazyRow {
            items(meals.value ?: emptyList()) {
                Card(
                    modifier = modifier
                        .width(150.dp)
                        .height(200.dp)
                        .padding(end = 16.dp)
                        .clickable {
                            navigator.push(FoodDetailsScreen(it.idMeal ?: "5577"))
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = it.strMealThumb,
                            contentDescription = "Image description",
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            placeholder = rememberAsyncImagePainter("https://example.com/placeholder.png"),
                            error = rememberAsyncImagePainter("https://example.com/error.png")
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.strMeal ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painterResource(id = R.drawable.heart),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = it.strCategory?:"",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painterResource(id = R.drawable.heart), contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "time",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }

    @Composable
    fun FoodCategoriesCard(application: Application) {

        val category = homeViewModel.categories.collectAsState()
        LazyRow {
            items(category.value ?: emptyList()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.darkBlue))
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = it.strCategoryThumb,
                            contentDescription = "card image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(color = colorResource(id = R.color.white)),
                            placeholder = rememberAsyncImagePainter("https://example.com/placeholder.png"),
                            error = rememberAsyncImagePainter("https://example.com/error.png")
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = it.strCategory ?: "",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = senFontFamily,
                                color = Color.White
                            )

                            // Use a Row to align the star and number together
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val numberStar = remember { mutableStateOf(Random.nextInt(2, 5)) }
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Star Icon",
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = numberStar.value.toString(),
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 4.dp),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

    }


}