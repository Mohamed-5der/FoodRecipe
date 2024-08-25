package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.LocationServices
import com.khedr.firebaselogin.R
import com.khedr.firebaselogin.presention.viewModel.HomeViewModel

class HomeScreen (val context: Context): Screen {
    lateinit var homeViewModel :HomeViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        homeViewModel  = hiltViewModel()
        Scaffold() {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        val selectedTabIndex = remember { mutableStateOf(1) }
        Scaffold(

            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        selectedTabIndex.value = 1
                    },
                    containerColor = Color(0xFFFF7622),
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .offset(y = 40.dp),

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chef),
                        contentDescription = "FAB Icon",
                        modifier = Modifier.size(24.dp)
                    )

                }
            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.White,
                    contentColor = Color(0xFFFF7622),
                    modifier = Modifier.height(70.dp)
                ) {
                    BottomNavigationItem(
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFFFF7622)
                                )
                                if (selectedTabIndex.value == 0) {
                                    Text(
                                        text = "Search",
                                        style = TextStyle(color = Color(0xFFFF7622)),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        },
                        selected = selectedTabIndex.value == 0,
                        onClick = { selectedTabIndex.value = 0 }
                    )
                    BottomNavigationItem(
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.home),
                                    contentDescription = "Home",
                                    modifier = Modifier.size(0.dp),
                                    tint = Color(0xFFFF7622)
                                )
                                if (selectedTabIndex.value == 1) {
                                    Text(
                                        text = "Home",
                                        style = TextStyle(color = Color(0xFFFF7622)),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        },
                        selected = selectedTabIndex.value == 1,
                        onClick = { selectedTabIndex.value = 1 }
                    )
                    BottomNavigationItem(
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painterResource(id = R.drawable.profile_click),
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFFFF7622)

                                )
                                if (selectedTabIndex.value == 2) {
                                    Text(
                                        text = "Profile",
                                        style = TextStyle(color = Color(0xFFFF7622)),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        },
                        selected = selectedTabIndex.value == 2,
                        onClick = { selectedTabIndex.value = 2 }
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedTabIndex.value) {
                    0 -> Navigator(SearchScreen())
                    1 -> HomeContent()
                    2 -> Navigator(ProfileScreen())
                }
            }
        }
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    fun HomeContent() {
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
            FoodCategoriesCard()
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.dish_of_the_day),
                fontFamily = senFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            Card(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Box {
                    val dishOfDay =homeViewModel.dishOfDay.collectAsState().value?.get(0)?: null
                    AsyncImage(
                        model = dishOfDay?.strMealThumb ,
                        contentDescription = "Image description",
                        modifier = Modifier
                            .fillMaxWidth().height(150.dp)
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
                        Toast.makeText(context, "Sea All", Toast.LENGTH_SHORT).show()
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
                        .padding(end = 16.dp),
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
                                    Icons.Default.Favorite,
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
                                    Icons.Default.Favorite, contentDescription = null,
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
        Spacer(modifier = Modifier.height(16.dp))

    }

    @Composable
    fun FoodCategoriesCard() {

        val category = homeViewModel.categories.collectAsState()
        LazyRow() {
            items(category.value ?: emptyList()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp).clickable {
                            Toast.makeText(context, it.strCategory, Toast.LENGTH_SHORT).show()
                        }
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
                            Spacer(modifier = Modifier.weight(1f))
                            Row(
                                modifier = Modifier.align(alignment = Alignment.CenterVertically)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Star Icon",
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "4.5",
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

val senFontFamily = FontFamily(
    Font(R.font.sen_regular, FontWeight.Normal),
    Font(R.font.sen_bold, FontWeight.Bold),
    Font(R.font.sen_medium, FontWeight.Medium),
    Font(R.font.sen_extra_bold, FontWeight.ExtraBold),
    Font(R.font.sen_semi_bold, FontWeight.SemiBold),
)
