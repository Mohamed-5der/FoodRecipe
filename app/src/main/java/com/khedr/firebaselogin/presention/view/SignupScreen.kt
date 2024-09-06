package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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

class SignupScreen(val firebaseViewModel: FireBaseViewModel) : Screen {
    var isLoading : Boolean = false

    @Composable
    override fun Content() {
        Signup(firebaseViewModel)
        isLoading = firebaseViewModel.isLoadingSignUp.collectAsState().value
        ShowProgressBar()
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

            Box{

            Image(painter = painterResource(id = R.drawable.logo_recipe), contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth().padding(20.dp)
                    .height(300.dp))

                Column(
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = androidx.compose.ui.Modifier.height(30.dp))
                    IconButton(onClick = { navigator.pop() },
                        modifier = Modifier.padding(start = 12.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = null,
                            modifier = androidx.compose.ui.Modifier.size(32.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 250.dp)
                )  {

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
                        text = stringResource(id = R.string.signup_subtitle),
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppins,
                        fontSize = 18.sp
                    )

                Card(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Column(modifier = androidx.compose.ui.Modifier.padding(16.dp)) {

                        Text(
                            text = stringResource(id = R.string.name_label),
                            color = colorResource(id = R.color.darkBlue),
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.name_placeholder)
                                ,color = colorResource(id = R.color.darkBlue),
                                fontSize = 14.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal) },
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.darkBlue),
                                unfocusedBorderColor = colorResource(id = R.color.darkBlue)
                            )

                        )

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
                            placeholder = { Text(text = stringResource(id = R.string.email_placeholder),
                                color = colorResource(id = R.color.darkBlue),
                                fontSize = 14.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal) },
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White) ,
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
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.password_label),
                                color = colorResource(id = R.color.darkBlue),
                                fontSize = 14.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal) },
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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.darkBlue),
                                unfocusedBorderColor = colorResource(id = R.color.darkBlue)
                            ),
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = stringResource(id = R.string.confirm_password_label),
                            color = colorResource(id = R.color.darkBlue),
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = confirmPassword.value,
                            onValueChange = { confirmPassword.value = it },
                            placeholder = { Text(text = stringResource(id = R.string.confirm_password_label)) },
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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.darkBlue),
                                unfocusedBorderColor = colorResource(id = R.color.darkBlue)
                            ),
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        Button(
                            onClick = {
                                if (name.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
                                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                }else if (password.value != confirmPassword.value) {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }else {
                                    register(email.value,password.value,navigator,context,name.value)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFFFF7622)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = stringResource(id = R.string.sign_up_button),
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }



    fun register(email: String, password: String, navigator: Navigator, context: Context,name :String){
        firebaseViewModel.register(email,password,{
            navigator.replaceAll(HomeScreen())
            sharedSave(name,email,context)
            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()

        })
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

    fun sharedSave (name : String,email : String,context: Context){
        val shared = getDefaultSharedPreferences(context)
        val nameSave:SharedPreferences.Editor = shared.edit()
        nameSave.putString("name",name)
        val emailSave:SharedPreferences.Editor = shared.edit()
        emailSave.putString("email",email)
        nameSave.apply()
        emailSave.apply()

    }
}

