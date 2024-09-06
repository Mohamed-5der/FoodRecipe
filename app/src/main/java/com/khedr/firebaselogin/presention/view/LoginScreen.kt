package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.khedr.firebaselogin.R
import kotlinx.coroutines.flow.StateFlow

class LoginScreen(private val fireBaseViewModel: FireBaseViewModel) : Screen {
     var isLoading : Boolean = false

    @Composable
    override fun Content() {
        Login(fireBaseViewModel)
        isLoading = fireBaseViewModel.isLoadingLogin.collectAsState().value
    }

    @Composable
    fun Login(fireBaseViewModel: FireBaseViewModel) {
        val context = LocalContext.current

        val navigator = LocalNavigator.currentOrThrow
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val passwordVisible = remember { mutableStateOf(false) }

        ShowProgressBar()
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            fireBaseViewModel.handleSignInResult(result.data, {
                navigator.push(HomeScreen())
            }, { error ->
                Toast.makeText(context, "Sign-in failed: $error", Toast.LENGTH_SHORT).show()
                 Log.e("LoginScreen", "Sign-in failed: $error")
            })
        }
        Box{
            Image(painter = painterResource(id = R.drawable.logo_recipe), contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 250.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_title),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.darkBlue),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppins,
                    fontSize = 30.sp
                )
                Text(
                    text = stringResource(id = R.string.login_subtitle),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppins,
                    fontSize = 18.sp
                )

                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = stringResource(id = R.string.email_label),
                            color = colorResource(id = R.color.darkBlue),
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.email_placeholder)) },
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            colors =OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.darkBlue),
                                unfocusedBorderColor = colorResource(id = R.color.darkBlue)
                            )

                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = stringResource(id = R.string.password_label),
                            color = colorResource(id = R.color.darkBlue),
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = androidx.compose.ui.Modifier.height(4.dp))

                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.password_label)) },
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisible.value)
                                    painterResource(id = R.drawable.eye)
                                else
                                    painterResource(id = R.drawable.eyeoff)

                                IconButton(onClick = {
                                    passwordVisible.value = !passwordVisible.value
                                }) {
                                    Icon(painter = image, contentDescription = null, modifier = Modifier.size(24.dp))
                                }
                            },
                            colors =OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.darkBlue),
                                unfocusedBorderColor = colorResource(id = R.color.darkBlue)
                            ),
                        )


                        Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))

                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            color = Color(0xFFFF7622),
                            fontSize = 14.sp,
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .clickable { },
                            fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal)),
                            textAlign = TextAlign.End
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                if (email.value.isEmpty() || password.value.isEmpty()) {
                                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                }else{
                                    login(email.value,password.value,context,navigator)
                                }
                            },
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFFFF7622)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = stringResource(id = R.string.login_button),
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp)
                        }

                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = stringResource(id = R.string.or),
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp), // Adds space between the text and lines
                                fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal)),
                                textAlign = TextAlign.Center
                            )

                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Spacer(modifier = Modifier.weight(1f))

                        }


                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = androidx.compose.ui.Modifier.fillMaxWidth()
                        ) {
                                Row (
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .border(
                                            BorderStroke(
                                                1.dp,
                                                colorResource(id = R.color.darkBlue)
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .fillMaxWidth()
                                        .clickable {
                                            launcher.launch(fireBaseViewModel.getSignInIntent())

                                        }
                                ){
                                    IconButton(onClick = {
                                        launcher.launch(fireBaseViewModel.getSignInIntent())
                                    },
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.google_logo),
                                            contentDescription = null,
                                            modifier = androidx.compose.ui.Modifier.size(48.dp)
                                        )
                                    }
                                    Text(text = "Login With Google",
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp)
                                }

                            Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))


                        }
                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

                        Row(
                            modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.dont_have_account),
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal)),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = stringResource(id = R.string.sign_up),
                                color = Color(0xFFFF7622),
                                fontSize = 16.sp,
                                modifier = androidx.compose.ui.Modifier
                                    .clickable { navigator.push(SignupScreen(fireBaseViewModel)) },
                                fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal)),
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
            }
        }
    }
    fun login(email: String, password: String, context: Context, navigator: Navigator){

        try {
            fireBaseViewModel.login(email,password,{
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                navigator.replaceAll(HomeScreen())
            },{
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show() }
            )
        }catch (e:Exception){
            Log.e("mohamed", "login: ${e.message}")
        }


    }
    @Composable
    fun ShowProgressBar(){

        if (isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }

    }

}
