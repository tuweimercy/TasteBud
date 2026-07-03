package com.example.tastebud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tastebud.ui.theme.TasteBudTheme
import com.example.tastebud.viewmodel.AuthState
import com.example.tastebud.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController,
                         authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var sent by remember { mutableStateOf(false) }
    val authState by authViewModel.authState.collectAsState()
    val isLoading = authState is AuthState.Loading
    val isResetSent = authState is AuthState.PasswordResetSent
    val errorMessage = (authState as? AuthState.Error)?.message

    // clean up the states when vacating this screen
    DisposableEffect(Unit) {
        onDispose { authViewModel.clearState() }
    }

    val pinkGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF06292),
            Color(0xFFE91E63),
            Color(0xFF880E4F)
        )
    )

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    Icons.Default.ArrowBack, 
                    contentDescription = "back",
                    tint = Color(0xFF880E4F)
                )
            }

            Spacer(Modifier.height(20.dp))
            
            // Logo Badge (Matching Splash/Login Structure)
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
                
                // Overlay icon - Positioned inside to stay within the circular badge
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
                text = "Reset Password",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF880E4F)
            )

            Spacer(Modifier.height(8.dp))

            if (isResetSent && sent) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE91E63).copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "Reset link has been sent. Kindly check your inbox!!",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF880E4F),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                
                Spacer(Modifier.height(16.dp))
                
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                ) {
                    Text("Back to Login", color = Color.White)
                }
            } else {
                Text(
                    text = "Enter your email and we will send you a link to reset your password",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFE91E63).copy(alpha = 0.7f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(Modifier.height(32.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = Color(0xFFE91E63)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),

                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black
                    ),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color(0xFFE91E63),
                        focusedBorderColor = Color(0xFFE91E63),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFFE91E63),
                        unfocusedLabelColor = Color.Gray
                    )
                )



                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = { sent = true
                        authViewModel.sendPasswordReset(email)},
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = email.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        disabledContainerColor = Color(0xFFE91E63).copy(alpha = 0.5f)
                    )
                ) {
                    Text(text = "Send Reset Link", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    TasteBudTheme {
        ForgotPasswordScreen(navController = rememberNavController())
    }
}
