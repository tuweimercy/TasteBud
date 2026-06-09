package com.example.tastebud.model

// enum class : static values that should not change
enum class UserRole {STUDENT,TEACHER}
data class UserProfile(
    val uid: String ="",
    val fullName: String ="",
    val email: String ="",
    val role: String = "student" // default student
){
    // Tomap will reference live values from firebase for credential checkup
    fun toMap(): Map<String, Any> = mapOf(
        "fullName" to fullName,
        "email" to email,
        "role" to role

    )
    fun userRole(): UserRole =
        if(role == "teacher") UserRole.TEACHER else UserRole.STUDENT
}