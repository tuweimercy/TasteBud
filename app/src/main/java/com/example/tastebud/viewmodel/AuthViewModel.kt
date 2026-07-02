package com.example.tastebud.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.tastebud.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val profile: UserProfile) : AuthState()
    data class Error(val message: String) : AuthState()
    object PasswordResetSent : AuthState()
    object Logout : AuthState()
}

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _currentProfile = MutableStateFlow<UserProfile?>(null)
    val currentProfile: StateFlow<UserProfile?> = _currentProfile

    init {
        val user = auth.currentUser
        if (user != null) {
            fetchUserProfile(user.uid)
        }
    }

    // Register
    fun register(
        fullName: String,
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _authState.value = AuthState.Loading

            try {

                val result = auth.createUserWithEmailAndPassword(
                    email,
                    password
                ).await()

                val uid = result.user!!.uid

                val profile = UserProfile(
                    uid = uid,
                    fullName = fullName,
                    email = email
                )

                db.collection("users")
                    .document(uid)
                    .set(profile.toMap())
                    .await()

                _currentProfile.value = profile
                _authState.value = AuthState.Success(profile)

            } catch (e: Exception) {

                _authState.value = AuthState.Error(
                    e.message ?: "Registration Failed"
                )

            }

        }

    }

    // Login
    fun Login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _authState.value = AuthState.Loading

            try {

                val result = auth
                    .signInWithEmailAndPassword(
                        email,
                        password
                    )
                    .await()

                fetchUserProfile(result.user!!.uid)

            } catch (e: Exception) {

                _authState.value = AuthState.Error(
                    e.message ?: "Login Failed"
                )

            }

        }

    }

    // Fetch Profile
    fun fetchUserProfile(uid: String) {

        viewModelScope.launch {

            try {

                _authState.value = AuthState.Loading

                val doc = db.collection("users")
                    .document(uid)
                    .get()
                    .await()

                val profile = UserProfile(
                    uid = uid,
                    fullName = doc.getString("fullName") ?: "",
                    email = doc.getString("email") ?: ""
                )

                _currentProfile.value = profile
                _authState.value = AuthState.Success(profile)

            } catch (e: Exception) {

                _authState.value = AuthState.Error(
                    e.message ?: "Failed to load profile"
                )

            }

        }

    }

    // Forgot Password
    fun sendPasswordReset(email: String) {

        viewModelScope.launch {

            _authState.value = AuthState.Loading

            try {

                auth.sendPasswordResetEmail(email).await()

                _authState.value = AuthState.PasswordResetSent

            } catch (e: Exception) {

                _authState.value = AuthState.Error(
                    e.message ?: "Reset Failed"
                )

            }

        }

    }

    // This lets you reset using the logged-in user's email
    fun resetPassword(email: String) {
        sendPasswordReset(email)
    }

    // Logout
    fun logout() {

        auth.signOut()

        _currentProfile.value = null

        _authState.value = AuthState.Logout

    }

    // Clear state
    fun clearState() {

        _authState.value = AuthState.Idle

    }

}