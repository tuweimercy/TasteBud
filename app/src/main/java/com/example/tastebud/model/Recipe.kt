package com.example.tastebud.model

import com.google.firebase.Timestamp

enum class UserRole {
    VIEWER,
    PUBLISHER
}

data class UserProfile(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val role: String = "viewer"
) {

    fun toMap(): Map<String, Any> {
        return mapOf(
            "fullName" to fullName,
            "email" to email,
            "role" to role
        )
    }

    fun userRole(): UserRole {
        return if (role.lowercase() == "publisher")
            UserRole.PUBLISHER
        else
            UserRole.VIEWER
    }
}

data class Recipe(

    val id: String = "",

    val title: String = "",

    val ingredients: String = "",

    val instructions: String = "",

    val imageUrl: String = "",

    val category: String = "",

    val cookingTime: Int = 0,

    val isPublic: Boolean = true,

    val ownerName: String = "",

    val ownerId: String = "",

    val likes: Int = 0,

    val uploadedAt: Timestamp = Timestamp.now()

) {

    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "title" to title,
            "ingredients" to ingredients,
            "instructions" to instructions,
            "imageUrl" to imageUrl,
            "category" to category,
            "cookingTime" to cookingTime,
            "isPublic" to isPublic,
            "ownerName" to ownerName,
            "ownerId" to ownerId,
            "likes" to likes,
            "uploadedAt" to uploadedAt
        )
    }
}