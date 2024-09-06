package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.khedr.firebaselogin.R
import com.khedr.firebaselogin.data.model.Meals
import com.khedr.firebaselogin.data.model.Recipe
import com.khedr.firebaselogin.presention.viewModel.DetailsViewModel
import com.khedr.firebaselogin.presention.viewModel.FavoriteDbViewModel
import kotlin.random.Random

class FoodDetailsScreen(private val id: String) : Screen {
lateinit var navigator : Navigator
lateinit var favoriteViewModel : FavoriteDbViewModel
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val detailsViewModel: DetailsViewModel = hiltViewModel()
        favoriteViewModel = hiltViewModel()
        detailsViewModel.getMealDetails(id)
        val isLoading by detailsViewModel.isLoading.collectAsState()
//        Scaffold(
//            containerColor = Color.White,
//            topBar = {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
//                ) {
//                    androidx.compose.material.TopAppBar(modifier = Modifier
//                        .background(Color.White)
//                        .height(80.dp),
//                        backgroundColor = Color.White,
//                        navigationIcon = {
//                            IconButton(
//                                onClick = { navigator.pop() },
//                                modifier = Modifier.padding(top = 20.dp)
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.ArrowBack,
//                                    contentDescription = null
//                                )
//                            }
//                        },
//                        title = {
//                            Spacer(modifier = Modifier.weight(0.4f))
//                            androidx.compose.material.Text(
//                                "Profile", modifier = Modifier
//                                    .padding(top = 20.dp),
//                                fontFamily = poppins,
//                                fontSize = 23.sp,
//                                fontWeight = FontWeight.SemiBold
//                            )
//                            Spacer(modifier = Modifier.weight(0.8f))
//                        },
//                        actions = {
//                            IconButton(
//                                onClick = { /* Handle favorite icon click */ },
//                                modifier = Modifier.padding(top = 20.dp)
//                            ) {
//                                Icon(
//                                    imageVector = /* Your favorite icon image vector */,
//                                    contentDescription = "Favorite"
//                                )
//                            }
//                        }
//                    )
//                }
//
//            }
//        ){
//
//        }
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        } else {

        }
        FoodDetails(detailsViewModel)


    }

    @Composable
    fun FoodDetails(detailsViewModel: DetailsViewModel) {
        navigator = LocalNavigator.currentOrThrow
        val favoriteIds by favoriteViewModel.favoriteIds.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            detailsViewModel.mealDetails.collectAsState().value?.let { mealDetails ->
                AsyncImage(
                    model = mealDetails.meals[0].strMealThumb,
                    contentDescription = null,
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
                        .padding(top = 30.dp, start = 8.dp, end = 8.dp)
                ) { Card(
                    modifier = Modifier.size(48.dp).padding(4.dp),
                    shape = RoundedCornerShape(20),
                ){
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Close",
                        modifier = Modifier.padding(8.dp)
                            .size(24.dp)
                            .clickable {
                                navigator.pop()
                            },
                        tint = Color.Black
                    )
                }


                    Spacer(modifier = Modifier.weight(1f))
                    val isFavorite = remember { mutableStateOf(favoriteIds.contains(mealDetails.meals[0].idMeal ?: "")) }
                    Card(
                        modifier = Modifier.size(48.dp).padding(4.dp),
                        shape = RoundedCornerShape(20),
                    ){
                        Image(
                            painterResource(id =
                            if (isFavorite.value) R.drawable.heart_select else R.drawable.heart),
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp)
                                .size(24.dp)
                                .clickable {
                                    isFavorite.value = !isFavorite.value
                                    if (isFavorite.value) {
                                        favoriteViewModel.addFavRecipe(
                                            Recipe(
                                                idMeal = mealDetails.meals[0].idMeal,
                                                strMeal = mealDetails.meals[0].strMeal ?: "",
                                                strMealThumb = mealDetails.meals[0].strMealThumb,
                                                isSelected = true
                                            )
                                        )
                                    } else {
                                        favoriteViewModel.deleteFavRecipe(mealDetails.meals[0].idMeal ?: "")
                                    }
                                }
                        )
                    }


                }

                FoodDescription(mealDetails.meals[0])
            }
        }
    }

    @Composable
    fun FoodDescription(meal: Meals) {
        val nonNullIngredients = getNonNullIngredients(meal)
        val nonNullMeasures = getNonNullMeasures(meal)

        var expanded = remember { mutableStateOf(false) }

        Card(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 280.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = meal.strMeal ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins,
                    color = colorResource(id = R.color.darkBlue)
                )
                Spacer(modifier = Modifier.height(8.dp))

                val descriptionToShow = if (expanded.value) meal.strInstructions ?: "" else {
                    meal.strInstructions?.take(100) + "......"
                }

                Text(
                    text = buildAnnotatedString {
                        append(descriptionToShow)
                        if (!expanded.value) {
                            append(" ")
                            withStyle(style = SpanStyle(
                                color = colorResource(id = R.color.orange),
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                            )) {
                                append("View More")
                            }
                        }
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.clickable {
                        expanded.value = !expanded.value
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                NutritionInfoRow()
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Ingredients",
                    color = colorResource(id = R.color.darkBlue),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    itemsIndexed(nonNullIngredients.filter { it.isNotBlank() }
                        .zip(nonNullMeasures.filter { it.isNotBlank() })) { index, (ingredient, measure) ->
                        Row(horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.circle),
                                contentDescription = "circle",
                                tint = colorResource(id = R.color.orange),
                                modifier = Modifier.size(9.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$ingredient : ",
                                color = colorResource(id = R.color.darkBlue),
                                fontFamily = poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                            Text(
                                text = measure,
                                color = Color.Gray,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                         val videoUrl = meal.strYoutube?.split("=")?.getOrNull(1) ?: ""
                         navigator.push(YouTubeVideoScreen(videoUrl))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFFFF7622)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Open Video")
                }
                Spacer(modifier = Modifier.height(16.dp))

            }



            }
        }
    @Composable
    fun NutritionInfoRow() {
        val numberKcal = remember { mutableStateOf(Random.nextInt(80, 150)) }
        val numberProtein = remember { mutableStateOf(Random.nextInt(5, 30)) }
        val numberFat = remember { mutableStateOf(Random.nextInt(60, 180)) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutritionInfoItem(
                icon = ImageVector.vectorResource(id = R.drawable.fire),
                contentDescription = "Calories",
                value = "${numberKcal.value} Kcal"
            )
            Spacer(modifier = Modifier.width(20.dp))
            NutritionInfoItem(
                icon = ImageVector.vectorResource(id = R.drawable.protien),
                contentDescription = "Protein",
                value = "${numberProtein.value}g Protein"
            )
            Spacer(modifier = Modifier.width(20.dp))
            NutritionInfoItem(
                icon = ImageVector.vectorResource(id = R.drawable.fat),
                contentDescription = "Fats",
                value = "${numberFat.value}g Fats"
            )
        }
    }

    @Composable
    fun NutritionInfoItem(
        icon: ImageVector,
        contentDescription: String,
        value: String,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

        ) {
            androidx.compose.material3.Card(
                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = value,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontFamily = poppins
            )
        }
    }
    }


    fun getNonNullIngredients(mealDetails: Meals): List<String> {
        return listOf(
            mealDetails.strIngredient1,
            mealDetails.strIngredient2,
            mealDetails.strIngredient3,
            mealDetails.strIngredient4,
            mealDetails.strIngredient5,
            mealDetails.strIngredient6,
            mealDetails.strIngredient7,
            mealDetails.strIngredient8,
            mealDetails.strIngredient9,
            mealDetails.strIngredient10,
            mealDetails.strIngredient11,
            mealDetails.strIngredient12,
            mealDetails.strIngredient13,
            mealDetails.strIngredient14,
            mealDetails.strIngredient15,
            mealDetails.strIngredient16,
            mealDetails.strIngredient17,
            mealDetails.strIngredient18,
            mealDetails.strIngredient19,
            mealDetails.strIngredient20
        ).filterNotNull()
    }
fun getNonNullMeasures(mealDetails: Meals): List<String> {
    return listOf(
        mealDetails.strMeasure1,
        mealDetails.strMeasure2,
        mealDetails.strMeasure3,
        mealDetails.strMeasure4,
        mealDetails.strMeasure5,
        mealDetails.strMeasure6,
        mealDetails.strMeasure7,
        mealDetails.strMeasure8,
        mealDetails.strMeasure9,
        mealDetails.strMeasure10,
        mealDetails.strMeasure11,
        mealDetails.strMeasure12,
        mealDetails.strMeasure13,
        mealDetails.strMeasure14,
        mealDetails.strMeasure15,
        mealDetails.strMeasure16,
        mealDetails.strMeasure17,
        mealDetails.strMeasure18,
        mealDetails.strMeasure19,
        mealDetails.strMeasure20
    ).filterNotNull()
}


