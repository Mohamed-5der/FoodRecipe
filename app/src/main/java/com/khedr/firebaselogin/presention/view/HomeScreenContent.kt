package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.annotation.SuppressLint
import android.widget.Toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.khedr.firebaselogin.R
import com.khedr.firebaselogin.data.model.Recipe
import com.khedr.firebaselogin.presentation.viewModel.HomeViewModel
import com.khedr.firebaselogin.presention.viewModel.FavoriteDbViewModel
import kotlin.random.Random

class HomeScreenContent : Screen {
    lateinit var homeViewModel: HomeViewModel
    lateinit var favoriteViewModel: FavoriteDbViewModel
    lateinit var navigator: Navigator
    lateinit var isSheetOpen: MutableState<Boolean>
    lateinit var isLoaddingBottom: State<Boolean>
    lateinit var categoryName: MutableState<String>
    lateinit var fireBaseViewModel: FireBaseViewModel


    @Composable
    override fun Content() {
        homeViewModel = hiltViewModel()
        favoriteViewModel = hiltViewModel()
        navigator = LocalNavigator.currentOrThrow
        val isLoading by homeViewModel.isLoading.collectAsState()
        isLoaddingBottom = homeViewModel.isLoadingBottomSheet.collectAsState()
        isSheetOpen = remember { mutableStateOf(false) }
        categoryName = remember { mutableStateOf("") }
        fireBaseViewModel=hiltViewModel()

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        } else {
            HomeContent(homeViewModel = homeViewModel, navigator = navigator)
            CreateBottomSheet()
        }
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    fun HomeContent(homeViewModel: HomeViewModel, navigator: Navigator) {
        val name = remember { mutableStateOf("Mohamed Khedr") }
        val context = LocalContext.current
        val currentUser by fireBaseViewModel.currentUser

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
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.darkBlue)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
//                        navigator.push(FavoriteScreen())
                            Toast.makeText(context, "Notification", Toast.LENGTH_SHORT).show()
                        },
                    tint = colorResource(id = R.color.orange)
                )
            }

            Text(
                text = name.value,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = colorResource(id = R.color.darkBlue)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.featured),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            FoodCategoriesCard()
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.dish_of_the_day),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            val dishOfDay = homeViewModel.dishOfDay.collectAsState().value?.firstOrNull()

            Card(
                onClick = {
                    dishOfDay?.idMeal?.let {
                        navigator.push(FoodDetailsScreen(it))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),

            ) {
                Box {
                    AsyncImage(
                        ImageRequest.Builder(LocalContext.current)
                            .data(dishOfDay?.strMealThumb)
                            .crossfade(true)
                            .placeholder(R.drawable.logo2)
                            .error(R.drawable.logo2)
                            .build(),
                        contentDescription = "Image description",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = colorResource(id = R.color.white)),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopEnd,
                    )
                    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
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
                                    fontFamily = poppins,
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
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(id = R.string.sea_all),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFFFF7622),
                    modifier = Modifier.clickable {
                        navigator.push(SearchScreen())
                    }
                )
            }
            RecipeCard()
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
    @Composable
    fun RecipeCard() {
        val meals by homeViewModel.meals.collectAsState()
        val favoriteIds by favoriteViewModel.favoriteIds.collectAsState()

        LazyRow {
            items(meals ?: emptyList()) { meal ->
                Card(
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .padding(end = 16.dp)
                        .clickable {
                            navigator.push(FoodDetailsScreen(meal.idMeal ?: "5577"))
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            ImageRequest.Builder(LocalContext.current)
                                .data(meal?.strMealThumb)
                                .crossfade(true)
                                .placeholder(R.drawable.logo2)
                                .error(R.drawable.logo2)
                                .build(),
                            contentDescription = "Image description",
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = meal.strMeal ?: "",
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = meal.strCategory ?: "",
                                    fontSize = 12.sp,
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.Normal,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                val isFavorite = remember { mutableStateOf(favoriteIds.contains(meal.idMeal ?: "")) }

                                Image(
                                    painterResource(id =
                                    if (isFavorite.value) R.drawable.heart_select else R.drawable.heart),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            isFavorite.value = !isFavorite.value
                                            if (isFavorite.value) {
                                                favoriteViewModel.addFavRecipe(
                                                    Recipe(
                                                        idMeal = meal.idMeal,
                                                        strMeal = meal.strMeal ?: "",
                                                        strMealThumb = meal.strMealThumb,
                                                        isSelected = true
                                                    )
                                                )
                                            } else {
                                                favoriteViewModel.deleteFavRecipe(meal.idMeal ?: "")
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun FoodCategoriesCard() {
        val categories by homeViewModel.categories.collectAsState()
        LazyRow {
            items(categories) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                        .clickable {
                            categoryName.value = it.strCategory ?: ""
                            isSheetOpen.value = true
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.darkBlue))
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            ImageRequest.Builder(LocalContext.current)
                                .data(it?.strCategoryThumb)
                                .crossfade(true)
                                .placeholder(R.drawable.logo2)
                                .error(R.drawable.logo2)
                                .build(),
                            contentDescription = "card image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = colorResource(id = R.color.white))
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
                                fontFamily = poppins,
                                color = Color.White
                            )

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
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateBottomSheet() {
        if (isSheetOpen.value) {
            val sheetState = rememberModalBottomSheetState()
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen.value = false
                },
                modifier = Modifier.height(600.dp)
            ) {
                CategoryChoose(categoryName.value)
            }
        }
    }
    @Composable
    fun CategoryChoose(category: String) {
        homeViewModel.getMealsByCategory(category)
        val meals = homeViewModel.mealsByCategory.collectAsState(initial = emptyList())
        val searchText = remember { mutableStateOf("") }

            if (meals.value.isNullOrEmpty()) {
                homeViewModel.getMealsByCategory(category)
            } else {
                Text(text = categoryName.value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth())
                OutlinedTextField(
                    value = searchText.value, onValueChange = { searchText.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.darkBlue),
                            RoundedCornerShape(16.dp)
                        ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                    placeholder = { Text(text = stringResource(id = R.string.search)) },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(colorResource(id = R.color.darkBlue))
                )
                LazyColumn(modifier = Modifier) {
                    items(meals.value?.filter {
                        it.strMeal?.contains(searchText.value, ignoreCase = true) == true
                    } ?: emptyList()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(it?.strMealThumb)
                                        .crossfade(true)
                                        .placeholder(R.drawable.logo2)
                                        .error(R.drawable.logo2)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(
                                        text = it.strMeal ?: "",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color(0xFF1D1E2C)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            model = it.strMealThumb,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = categoryName.value ?: "",
                                            fontSize = 14.sp,
                                            color = Color(0xFF7E8A9A)
                                        )
                                    }
                                }
                                Image(painter = painterResource(id = R.drawable.arrow_right),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            navigator.push(FoodDetailsScreen(it.idMeal ?: ""))
                                        })

                            }
                        }
                    }
                }
        }
    }


}
