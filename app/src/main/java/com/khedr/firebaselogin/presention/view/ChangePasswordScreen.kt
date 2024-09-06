package com.khedr.firebaselogin.presention.view

import FireBaseViewModel
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.khedr.firebaselogin.R

class ChangePasswordScreen : Screen {
    lateinit var fireBaseViewModel: FireBaseViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val context = LocalContext.current
        fireBaseViewModel= hiltViewModel()
        val passwordVisible = remember { mutableStateOf(false) }
        var newPassword by remember { mutableStateOf("") }
        var oldPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        val navagation = LocalNavigator.currentOrThrow
        Scaffold (
            topBar = {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ){
                    TopAppBar(modifier = Modifier
                        .background(Color.White)
                        .height(80.dp),
                        backgroundColor = Color.White,
                        navigationIcon = {
                            IconButton(onClick = { navagation.pop()},
                                modifier = Modifier.padding(top = 20.dp)) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                            }
                        },
                        title = {
                            Spacer(modifier = Modifier.weight(0.4f))
                            Text("Change Password", modifier = Modifier
                            .padding(top = 20.dp),
                            fontFamily = poppins,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.weight(0.8f))
                        })
                }

            }
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 100.dp, start = 20.dp, end = 20.dp)
            ){
                    androidx.compose.material3.Text(
                        text = "Old password",
                        color = colorResource(id = R.color.darkBlue),
                        fontSize = 14.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        placeholder = { androidx.compose.material3.Text(text = "Old password") },
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
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

                    androidx.compose.material3.Text(
                        text = "New password",
                        color = colorResource(id = R.color.darkBlue),
                        fontSize = 14.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        placeholder = { androidx.compose.material3.Text(text = "New password") },
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
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

                androidx.compose.material3.Text(
                    text ="Confirm New password",
                    color = colorResource(id = R.color.darkBlue),
                    fontSize = 14.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { androidx.compose.material3.Text(text ="Confirm New password",
                    ) },
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
                        if (newPassword != confirmPassword) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()

                        }else{
                            fireBaseViewModel.updatePassword(oldPassword = oldPassword, newPassword = newPassword,{
                                Toast.makeText(context,"Password Changed Successfully",Toast.LENGTH_SHORT).show()
                                navagation.pop()
                            },{
                                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
                            })
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFFFF7622)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.submit),
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp)
                }
                }

        }
    }
}