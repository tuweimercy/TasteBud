package com.example.tastebud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
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
fun RegisterScreen(navController: NavController,authViewModel: AuthViewModel) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("Student") }
    val roles = listOf("Student", "Teacher")
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
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Register.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF1F3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Logo Badge with Picture
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                // Circular Image with Border
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .shadow(12.dp, CircleShape)
                        .border(4.dp, pinkGradient, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1000&auto=format&fit=crop",
                        contentDescription = "Logo Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                // Overlay icon - Moved outside the clip and adjusted position
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 4.dp, end = 4.dp)
                        .size(36.dp)
                        .shadow(6.dp, CircleShape)
                        .background(pinkGradient, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
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

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF880E4F)
            )
            
            Text(
                text = "Join TasteBud Today",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFE91E63).copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFFE91E63)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFFE91E63)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
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
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFFE91E63)) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "Select Role",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start),
                color = Color(0xFF880E4F)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                roles.forEach { role ->
                    val selected = selectedRole == role
                    FilterChip(
                        selected = selected,
                        onClick = { selectedRole = role },
                        label = { Text(role) },
                        leadingIcon = {
                            Icon(
                                if (role == "Teacher") Icons.Default.School else Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFE91E63),
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color.White
                        )
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            ) {
                Text(
                    text = "Already have an account? Sign in",
                    color = Color(0xFF880E4F),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    TasteBudTheme {
        RegisterScreen(navController = rememberNavController())
    }
}
