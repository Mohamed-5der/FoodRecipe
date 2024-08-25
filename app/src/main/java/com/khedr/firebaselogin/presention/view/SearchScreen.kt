package com.khedr.firebaselogin.presention.view

import android.widget.ImageButton
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.khedr.firebaselogin.R
import java.nio.file.WatchEvent

class SearchScreen :Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val searchText = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                IconButton(onClick = { navigator.pop()},modifier = Modifier.size(40.dp)) {
                    Icon(painter = painterResource(id = R.drawable.arrow_left),contentDescription = "Back",)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.search),
                    fontSize = 24.sp,
                    fontFamily = senFontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                    )
                Spacer(modifier = Modifier.weight(1.2f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchText.value, onValueChange = { searchText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colorResource(id = R.color.darkBlue), RoundedCornerShape(16.dp)),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                placeholder = { Text(text = stringResource(id = R.string.search)) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(colorResource(id = R.color.darkBlue))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.category),
                fontFamily = senFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            CategoryCard()

            }

        }

    }
    @Composable
    fun CategoryCard() {
        LazyColumn {
           items(10) {
               Spacer(modifier = Modifier.height(16.dp))
               Card(
                   shape = RoundedCornerShape(16.dp),
                   modifier = Modifier
                       .fillMaxWidth()
                   ,
                   elevation = CardDefaults.cardElevation(8.dp),
                   colors = CardDefaults.cardColors(containerColor = Color.White)
               ) {
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(16.dp),
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Image(
                           painter = painterResource(id = R.drawable.food),
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
                               text = "Food Title",
                               fontWeight = FontWeight.Bold,
                               fontSize = 18.sp,
                               color = Color(0xFF1D1E2C)
                           )
                           Spacer(modifier = Modifier.height(8.dp))
                           Row(
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Image(
                                   painter = painterResource(id = R.drawable.food),
                                   contentDescription = null,
                                   contentScale = ContentScale.Crop,
                                   modifier = Modifier
                                       .size(24.dp)
                                       .clip(CircleShape)
                               )
                               Spacer(modifier = Modifier.width(8.dp))
                               Text(
                                   text = "Food",
                                   fontSize = 14.sp,
                                   color = Color(0xFF7E8A9A)
                               )
                           }
                       }
                       Image(painter = painterResource(id = R.drawable.arrow_right), contentDescription = "",
                           modifier = Modifier
                               .size(24.dp)
                               .clickable {
                                   // navigator.push(FoodDetailsScreen())
                               })

                   }
               }
            }

        }

    }
