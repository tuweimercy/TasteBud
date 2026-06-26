package com.example.tastebud.ui.screens

import android.R.attr.enabled
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tastebud.navigation.Screen
import com.example.tastebud.ui.theme.TasteBudTheme
import com.example.tastebud.viewmodel.AuthState
import com.example.tastebud.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController,
                authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val authState by authViewModel.authState.collectAsState()
    val isLoading = authState is AuthState.Loading
    val errorMessage = (authState as? AuthState.Error)?.message



    val pinkGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF06292),
            Color(0xFFE91E63),
            Color(0xFF880E4F)
        )
    )
    LaunchedEffect(authState
    ) {
        if(authState is AuthState.Success){
            navController.navigate(Screen.Dashboard.route){
                popUpTo(Screen.Login.route) {inclusive = true}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF1F3)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(40.dp))
            
            // Logo Badge (Matching Splash Screen Structure)
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .shadow(12.dp, CircleShape)
                    .border(4.dp, pinkGradient, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1000&auto=format&fit=crop",
                    contentDescription = "Logo Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Overlay icon - Positioned inside to avoid clipping
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 12.dp, end = 12.dp)
                        .size(40.dp)
                        .shadow(4.dp, CircleShape)
                        .background(pinkGradient, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                text = "TasteBud",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF880E4F),
                    letterSpacing = 2.sp
                )
            )
            
            Spacer(Modifier.height(24.dp))
            
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF880E4F)
            )
            
            Text(
                text = "Sign in to your account",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFE91E63).copy(alpha = 0.7f)
            )
            
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFFE91E63)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFFE91E63)) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = Color(0xFFE91E63)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            )
            
            Spacer(Modifier.height(8.dp))
            
            TextButton(
                onClick = { navController.navigate(Screen.ForgotPassword.route) },
                modifier = Modifier.align(Alignment.End),
            ) {
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFFE91E63)
                )
            }
            
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    authViewModel.Login(email,password)
                },
                modifier= Modifier.fillMaxWidth().height(52.dp),
                shape=RoundedCornerShape(12.dp),
                colors= ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                enabled= email.isNotBlank() && password.isNotBlank()
                        && !isLoading
            ) {
                if(isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Sign In",style=MaterialTheme.typography
                        .bodyLarge)
                }
            }
            
//            Button(
//                onClick = {},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(52.dp),
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
//            ) {
//                Text(
//                    text = "Sign in",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = Color.White
//                )
//            }
            
            Spacer(Modifier.height(16.dp))
            
            TextButton(
                onClick = {

                    authViewModel.clearState()
                    navController.navigate(Screen.Register.route) }
            ) {
                Text(
                    text = "Don't have an account? Register",
                    color = Color(0xFF880E4F),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TasteBudTheme {
        LoginScreen(navController = rememberNavController())
    }
}
