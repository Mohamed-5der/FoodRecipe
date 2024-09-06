package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.annotation.SuppressLint
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.khedr.firebaselogin.R

class ProfileScreen : Screen {
    lateinit var fireBaseViewModel: FireBaseViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        fireBaseViewModel = hiltViewModel()
        val currentUser by fireBaseViewModel.currentUser
        val navigator = LocalNavigator.currentOrThrow
        val showDialog = remember { mutableStateOf(false) }
        val context = LocalContext.current

        Scaffold(
            containerColor = Color.White,
            topBar = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    androidx.compose.material.TopAppBar(modifier = Modifier
                        .background(Color.White)
                        .height(80.dp),
                        backgroundColor = Color.White,
                        navigationIcon = {
                            IconButton(
                                onClick = { navigator.pop() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Spacer(modifier = Modifier.weight(0.4f))
                            androidx.compose.material.Text(
                                "Profile", modifier = Modifier
                                    .padding(top = 20.dp),
                                fontFamily = poppins,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.weight(0.8f))
                        })
                }

            }
        ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp, start = 16.dp, end = 16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.size(16.dp))

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center

            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(120.dp),
                ) {
                    AsyncImage(
                        model = currentUser?.photoUrl ?: R.drawable.profile,
                        contentDescription = "Add Photo",
                        modifier = Modifier
                            .background(Color.White, CircleShape)
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Card (shape = CircleShape,
                    colors = CardDefaults.cardColors(Color.White),
                    modifier = Modifier.size(36.dp).align(Alignment.BottomEnd).clickable {

                    }){
                    Image(painter = painterResource(id = R.drawable.edit), contentDescription = "",
                        modifier = Modifier
                            .size(36.dp).padding(4.dp))
                }



            }
            Spacer(modifier = Modifier.width(12.dp))

            SettingsScreen(currentUser, navigator, showDialog)

        }
    }
    }

    @Composable
    fun SettingsScreen(currentUser : FirebaseUser?,navigator: Navigator,showDialog: MutableState<Boolean>) {
        val context = LocalContext.current
        val shared = getDefaultSharedPreferences(context)

        val email = shared.getString("email", "Email@gmail.com") ?: "Email@gmail.com"
        val name = shared.getString("name", "Mohamed Khedr") ?: "Mohamed Khedr"
        val phone = shared.getString("phone", "01203898109") ?: "M01203898109"

        Column(
            modifier = Modifier
                .fillMaxSize().padding(top = 30.dp),

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 12.dp)
                    .border(
                        1.dp, colorResource(id = R.color.darkBlue),
                        RoundedCornerShape(10.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF2F4F4F)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("UserName : ", fontSize = 18.sp, color = colorResource(
                        id = R.color.darkBlue),fontWeight = FontWeight.Medium, fontFamily = poppins)

                    Text(text = name.toString()?:"Mohamed Khedr", fontSize = 14.sp, color = colorResource(
                        id = R.color.darkBlue),fontWeight = FontWeight.Normal, fontFamily = poppins)

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .height(50.dp)
                    .border(
                        1.dp, colorResource(id = R.color.darkBlue),
                        RoundedCornerShape(10.dp)
                    ),
            verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF2F4F4F)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Email : ", fontSize = 18.sp, color = colorResource(
                        id = R.color.darkBlue),fontWeight = FontWeight.Medium, fontFamily = poppins)

                    Text(text = currentUser?.email.toString(), fontSize = 14.sp, color = colorResource(
                        id = R.color.darkBlue),fontWeight = FontWeight.Normal, fontFamily = poppins)

                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .height(50.dp)
                    .border(
                        1.dp, colorResource(id = R.color.darkBlue),
                        RoundedCornerShape(10.dp)
                    ),
            verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF2F4F4F)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Phone : ", fontSize = 18.sp, color = colorResource(
                        id = R.color.darkBlue),fontWeight = FontWeight.Medium, fontFamily = poppins)

                    Text(text ="01203898109", fontSize = 16.sp, color = colorResource(
                        id = R.color.darkBlue),fontWeight = FontWeight.Normal, fontFamily = poppins)

                }

            }





        }
    }
//    Row(
//    modifier = Modifier.padding(vertical = 8.dp)
//    ) {
//        Icon(icon, contentDescription = title, modifier = Modifier.padding(end = 16.dp))
//        Column {
//            Text(title, fontWeight = FontWeight.Bold)
//            Text(value)
//        }
//    }

}
