package com.example.tastebud.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.tastebud.model.Recipe
import com.example.tastebud.utils.CloudinaryUploader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RecipeViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _publicRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val publicRecipes: StateFlow<List<Recipe>> = _publicRecipes

    private val _myRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val myRecipes: StateFlow<List<Recipe>> = _myRecipes

    private val _recipeState = MutableStateFlow<RecipeState>(RecipeState.Idle)
    val recipeState: StateFlow<RecipeState> = _recipeState

    private val _uploadProgress = MutableStateFlow(0f)
    val uploadProgress: StateFlow<Float> = _uploadProgress

    fun loadPublicRecipes() {

        viewModelScope.launch {

            try {

                val snapshot = db.collection("recipes")
                    .orderBy("uploadedAt", Query.Direction.DESCENDING)
                    .get()
                    .await()

                _publicRecipes.value =
                    snapshot.documents.mapNotNull {

                        it.toObject(Recipe::class.java)?.copy(id = it.id)

                    }

            } catch (e: Exception) {

                _recipeState.value =
                    RecipeState.Error(e.message ?: "Failed to load recipes")

            }

        }

    }

    fun loadMyRecipes() {

        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {

            try {

                val snapshot = db.collection("recipes")
                    .whereEqualTo("ownerId", uid)
                    .orderBy("uploadedAt", Query.Direction.DESCENDING)
                    .get()
                    .await()

                _myRecipes.value =
                    snapshot.documents.mapNotNull {

                        it.toObject(Recipe::class.java)?.copy(id = it.id)

                    }

            } catch (e: Exception) {

                _recipeState.value =
                    RecipeState.Error(e.message ?: "Failed to load recipes")

            }

        }

    }

    fun uploadRecipe(
        context: Context,
        recipeName: String,
        ingredients: String,
        instructions: String,
        category: String,
        imageUri: Uri,
        ownerName: String
    ) {

        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {

            _recipeState.value = RecipeState.Loading

            try {

                val imageUrl =
                    CloudinaryUploader.uploadImage(
                        context = context,
                        imageUri = imageUri,
                        onProgress = {
                            _uploadProgress.value = it
                        }
                    )

                val recipe = Recipe(
                    title = recipeName,
                    ingredients = ingredients,
                    instructions = instructions,
                    imageUrl = imageUrl,
                    category = category,
                    ownerName = ownerName,
                    ownerId = uid
                )

                db.collection("recipes")
                    .add(recipe.toMap())
                    .await()

                _uploadProgress.value = 0f

                _recipeState.value = RecipeState.Success

            } catch (e: Exception) {

                _recipeState.value =
                    RecipeState.Error(e.message ?: "Upload failed")

            }

        }

    }
    fun updateRecipe(
        recipeId: String,
        recipeName: String,
        ingredients: String,
        instructions: String,
        category: String
    ) {

        viewModelScope.launch {

            _recipeState.value = RecipeState.Loading

            try {

                db.collection("recipes")
                    .document(recipeId)
                    .update(
                        mapOf(
                            "title" to recipeName,
                            "ingredients" to ingredients,
                            "instructions" to instructions,
                            "category" to category
                        )
                    )
                    .await()

                _recipeState.value = RecipeState.Success

            } catch (e: Exception) {

                _recipeState.value =
                    RecipeState.Error(e.message ?: "Update failed")

            }

        }

    }

    fun deleteRecipe(recipe: Recipe) {

        viewModelScope.launch {

            try {

                db.collection("recipes")
                    .document(recipe.id)
                    .delete()
                    .await()

                _recipeState.value = RecipeState.Success

            } catch (e: Exception) {

                _recipeState.value =
                    RecipeState.Error(e.message ?: "Delete failed")

            }

        }

    }

    fun clearState() {

        _recipeState.value = RecipeState.Idle

    }

}