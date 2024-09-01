package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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

class LoginScreen(private val fireBaseViewModel: FireBaseViewModel) : Screen {
    @Composable
    override fun Content() {
        Login(fireBaseViewModel)
    }

    @Composable
    fun Login(fireBaseViewModel: FireBaseViewModel) {
        val context = LocalContext.current

        val navigator = LocalNavigator.currentOrThrow
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val passwordVisible = remember { mutableStateOf(false) }


        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            fireBaseViewModel.handleSignInResult(result.data, {
                navigator.push(HomeScreen())
            }, { error ->
                Toast.makeText(context, "Sign-in failed: $error", Toast.LENGTH_SHORT).show()
                android.util.Log.e("LoginScreen", "Sign-in failed: $error")
            })
        }

        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E2E))
        ) {
            Image(
                painter = painterResource(id = R.drawable.asset),
                contentDescription = null,
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(Color(0xFF5A5A62))
            )
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = androidx.compose.ui.Modifier.height(120.dp))

                Text(
                    text = stringResource(id = R.string.login_title),
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )

                Text(
                    text = stringResource(id = R.string.login_subtitle),
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = androidx.compose.ui.Modifier.height(50.dp))

                Card(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Column(modifier = androidx.compose.ui.Modifier.padding(16.dp)) {
                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

                        Text(
                            text = stringResource(id = R.string.email_label),
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal))
                        )

                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.email_placeholder)) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF0F5FA))
                        )

                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

                        Text(
                            text = stringResource(id = R.string.password_label),
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal))
                        )

                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.password_label)) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF0F5FA)),
                            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisible.value)
                                    painterResource(id = R.drawable.eye)
                                else
                                    painterResource(id = R.drawable.eyeoff )

                                IconButton(onClick = {
                                    passwordVisible.value = !passwordVisible.value
                                }) {
                                    Icon(painter = image, contentDescription = null,modifier = androidx.compose.ui.Modifier.size(24.dp))
                                }
                            }
                        )

                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

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

                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

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
                            Text(text = stringResource(id = R.string.login_button))
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

                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

                        Text(
                            text = stringResource(id = R.string.or),
                            color = Color.Black,
                            fontSize = 16.sp,
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth(),
                            fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal)),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = androidx.compose.ui.Modifier.height(14.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier =  androidx.compose.ui.Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = {},
                                modifier =  androidx.compose.ui.Modifier.weight(1f)) {
                                Image(
                                    painter = painterResource(id = R.drawable.x_logo),
                                    contentDescription = null
                                )
                            }

                            IconButton(onClick = {
                                launcher.launch(fireBaseViewModel.getSignInIntent())
                            },
                                modifier = androidx.compose.ui.Modifier.weight(1f)) {
                                Image(
                                    painter = painterResource(id = R.drawable.google_logo),
                                    contentDescription = null,
                                    modifier = androidx.compose.ui.Modifier.size(200.dp)
                                )
                            }

                            IconButton(onClick = {},
                                modifier =  androidx.compose.ui.Modifier.weight(1f)) {
                                Image(
                                    painter = painterResource(id = R.drawable.face_logo),
                                    contentDescription = null
                                )
                            }
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
                navigator.push(HomeScreen())
            },{
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show() }
            )
        }catch (e:Exception){
            Log.e("mohamed", "login: ${e.message}")
        }


    }

}
