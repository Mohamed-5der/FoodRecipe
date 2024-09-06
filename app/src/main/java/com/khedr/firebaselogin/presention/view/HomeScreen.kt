package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen



import com.khedr.firebaselogin.R

class HomeScreen : Screen {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        Scaffold() {
            MainScreen()
        }
    }


    @Composable
    fun MainScreen() {
        val selectedTabIndex = remember { mutableStateOf(0) }
        Scaffold(
//            floatingActionButtonPosition = FabPosition.Center,
//            floatingActionButton = {
//                FloatingActionButton(
//                    onClick = {
//                        selectedTabIndex.value = 1
//                    },
//                    containerColor = Color.Transparent,
//                    contentColor = Color.White,
//                    shape = CircleShape,
//                    modifier = Modifier
//                        .offset(y = 56.dp)
//                        ,
//                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
//
//                    ) {
//                    BottomHalfCircleDrawing()
//                    Box(
//                        modifier = Modifier
//                            .size(60.dp)
//                            .clip(CircleShape)
//                            .background(Color(0xFFFF7622)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.chef),
//                            contentDescription = "FAB Icon",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//
//
//                }
//            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.White,
                    contentColor = Color(0xFFFF7622),
                    modifier = Modifier
                        .height(70.dp)
                        .border(1.dp, Color(0xFFF3F1F0)),
                ) {
                    BottomNavigationItem(
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painterResource(
                                        id = if (selectedTabIndex.value == 0) R.drawable.home else R.drawable.home_un
                                    ),
                                    contentDescription = "home",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFFFF7622)
                                )
                                if (selectedTabIndex.value == 0) {
                                    Text(
                                        text = "Home",
                                        style = TextStyle(color = Color(0xFFFF7622)),
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
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
                                    painterResource(
                                        id = if (selectedTabIndex.value == 1) R.drawable.search_select else R.drawable.menu_icon
                                    ),
                                    contentDescription = "search",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFFFF7622)
                                )
                                if (selectedTabIndex.value == 1) {
                                    Text(
                                        text = "Search",
                                        style = TextStyle(color = Color(0xFFFF7622)),
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
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
                                    painterResource(
                                        id = if (selectedTabIndex.value == 2) R.drawable.heart_select else R.drawable.heart
                                    ),
                                    contentDescription = "favorite",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFFFF7622)
                                )
                                if (selectedTabIndex.value == 2) {
                                    Text(
                                        text = "Favorite",
                                        style = TextStyle(color = Color(0xFFFF7622)),
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        },
                        selected = selectedTabIndex.value == 2,
                        onClick = { selectedTabIndex.value = 2 }
                    )

                    BottomNavigationItem(
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    if (selectedTabIndex.value == 3) Icons.Filled.Settings  else Icons.Outlined.Settings
                                    ,
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFFFF7622)
                                )
                                if (selectedTabIndex.value == 3) {
                                    Text(
                                        text = "Settings",
                                        style = TextStyle(color = Color(0xFFFF7622)),
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        },
                        selected = selectedTabIndex.value == 3,
                        onClick = { selectedTabIndex.value = 3 }
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedTabIndex.value) {
                    0 -> HomeScreenContent().Content()
                    1 -> SearchScreen().Content()
                    2 -> FavoriteScreen().Content()
                    3 -> SettingsScreen().Content()
                }
            }
        }
    }
//
//    @Composable
//    fun BottomHalfCircleDrawing() {
//        Canvas(modifier = Modifier.size(80.dp)) {
//            drawArc(
//                color = Color(0xFFFEFAFD),
//                startAngle = 0f,
//                sweepAngle = 180f,
//                useCenter = true,
//                size = Size(size.width, size.height)
//            )
//
//            drawArc(
//                color = Color(0xFFF3F1F0),
//                startAngle = 0f,
//                sweepAngle = 180f,
//                useCenter = false,
//                style = Stroke(width = 2.dp.toPx()),
//                size = Size(size.width, size.height)
//            )
//        }
//    }

}

val poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.ExtraBold),
)
