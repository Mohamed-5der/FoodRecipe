package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.khedr.firebaselogin.presention.viewModel.FavoriteDbViewModel

class FavoriteScreen : Screen {
    lateinit var favoriteViewModel: FavoriteDbViewModel
    lateinit var navigator: Navigator
    lateinit var searchText : MutableState<String>

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @Composable
    override fun Content() {
         favoriteViewModel = hiltViewModel()
         navigator = LocalNavigator.currentOrThrow
         searchText = remember { mutableStateOf("") }
        Scaffold (
            containerColor = Color.White,
            topBar = {
                TopAppBar(modifier = Modifier.background(Color.White),
                    backgroundColor = Color.White,
                    title = { androidx.compose.material.Text("Favorite", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = poppins,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold) })
            }
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, start = 16.dp, end = 16.dp)
            ) {
                OutlinedTextField(
                    value = searchText.value, onValueChange = { searchText.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, colorResource(id = R.color.darkBlue), RoundedCornerShape(16.dp)),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                    placeholder = { androidx.compose.material3.Text(text = stringResource(id = R.string.favorite)) },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(colorResource(id = R.color.darkBlue))
                )
                Spacer(modifier = Modifier.height(16.dp))
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.category),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                CategoryCard()

            }
        }

    }

    @Composable
    fun CategoryCard() {
        val mealsSearch = favoriteViewModel.favRecipes.collectAsState()
        LazyColumn {
            items(mealsSearch.value?.filter {
                it.strMeal?.contains(searchText.value, ignoreCase = true) == true
            } ?: emptyList()) {
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp).clickable {
                            navigator.push(FoodDetailsScreen(it.idMeal ?: ""))
                        },
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
                                .error(R.drawable.logo2
                                )
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
                            androidx.compose.material3.Text(
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
                            }
                        }
                        Image(painter = painterResource(id = R.drawable.heart_select),
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    favoriteViewModel.deleteFavRecipe(it.idMeal?:"")
                                })

                    }
                }
                Spacer(modifier = Modifier.height(4 .dp))

            }

        }

    }

}