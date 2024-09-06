package com.khedr.firebaselogin.presention.view

import android.annotation.SuppressLint
import android.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.khedr.firebaselogin.R

class SettingsScreen : Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold (
            containerColor = Color.White,
            topBar = {
                TopAppBar(modifier = Modifier.background(Color.White),
                    backgroundColor = Color.White,
                    title = { Text("Settings", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                         fontFamily = poppins,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold) })
            }
        ){
            Column (modifier = Modifier.background(Color.White)){
                SettingsScreenContent(navigator)
            }

        }
    }

    @Composable
    fun SettingsScreenContent(navigator: Navigator) {
        val context = LocalContext.current
        val shared = getDefaultSharedPreferences(context)
        Column (modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp)) {
            Row (
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth().clickable {
                        navigator.push(ProfileScreen())
                    }
                    .padding(bottom = 24.dp)
            ){
                Icon(Icons.Outlined.AccountCircle,
                    contentDescription = "" ,
                    modifier = Modifier.padding(end = 16.dp),
                    tint = colorResource(id = R.color.darkBlue))
                Text("Profile", fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.darkBlue)
                )
            }
            Row (
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth().clickable {
                        navigator.push(ChangePasswordScreen())
                    }
                    .padding(bottom = 24.dp)
            ){
                Icon(
                    painterResource(id = R.drawable.eye1),
                    contentDescription = "" ,
                    modifier = Modifier.padding(end = 16.dp),
                    tint = colorResource(id = R.color.darkBlue))
                Text("Change Password", fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.darkBlue)
                )
            }
            Row (
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ){
                Icon(
                    Icons.Outlined.Call,
                    contentDescription = "" ,
                    modifier = Modifier.padding(end = 16.dp),
                    tint = colorResource(id = R.color.darkBlue))
                Text("Support", fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.darkBlue)
                )
            }

            Row (
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ){
                Icon(
                    Icons.Outlined.Lock,
                    contentDescription = "" ,
                    modifier = Modifier.padding(end = 16.dp),
                    tint = colorResource(id = R.color.darkBlue))
                Text("Privacy", fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.darkBlue)
                )
            }
            Row (
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ){
                Icon(
                    painterResource(id = R.drawable.logout),
                    contentDescription = "" ,
                    modifier = Modifier.padding(end = 16.dp),
                    tint = colorResource(id = R.color.darkBlue))
                Text("Log Out", fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.darkBlue)
                )
            }
        }


    }


    }


