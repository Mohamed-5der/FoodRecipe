package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

class SignupScreen(val firebaseViewModel: FireBaseViewModel) : Screen {
    @Composable
    override fun Content() {
        Signup(firebaseViewModel)
    }

    @Composable
    fun Signup(firebaseViewModel: FireBaseViewModel) {
        val context = LocalContext.current

        val navigator = LocalNavigator.currentOrThrow
        val name = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }
        val passwordVisible = remember { mutableStateOf(false) }

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
                Spacer(modifier = androidx.compose.ui.Modifier.height(50.dp))

                Row(
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Spacer(modifier = androidx.compose.ui.Modifier.width(12.dp))
                    IconButton(onClick = { navigator.pop() }) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            modifier = androidx.compose.ui.Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = androidx.compose.ui.Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.signup_title),
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                Text(
                    text = stringResource(id = R.string.signup_subtitle),
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
                            text = stringResource(id = R.string.name_label),
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal))
                        )

                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.name_placeholder)) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF0F5FA))
                        )

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
                            text = stringResource(id = R.string.confirm_password_label),
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.sen_regular, FontWeight.Normal))
                        )

                        OutlinedTextField(
                            value = confirmPassword.value,
                            onValueChange = { confirmPassword.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.confirm_password_placeholder)) },
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

                        Spacer(modifier = androidx.compose.ui.Modifier.height(46.dp))

                        Button(
                            onClick = {
                                register(email.value,password.value,navigator,context)
                            },
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFFFF7622)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = stringResource(id = R.string.sign_up_button))
                        }
                    }
                }
            }
        }
    }



    fun register(email: String, password: String, navigator: Navigator, context: Context){
        firebaseViewModel.register(email,password,{
            navigator.push(HomeScreen())
            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()

        })
    }
}
