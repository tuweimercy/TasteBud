package com.example.tastebud.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tastebud.viewmodel.RecipeState
import com.example.tastebud.viewmodel.AuthViewModel
import com.example.tastebud.viewmodel.RecipeViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun UploadRecipeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    recipeViewModel: RecipeViewModel = viewModel()
) {

    val Pink = Color(0xFFE91E63)

    val context = LocalContext.current

    var recipeName by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    var category by remember { mutableStateOf("Breakfast") }

    var recipeImageUri by remember { mutableStateOf<Uri?>(null) }

    val categories = listOf(
        "Breakfast",
        "Lunch",
        "Dinner",
        "Dessert",
        "Snacks",
        "Drinks"
    )

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            recipeImageUri = uri
        }
    val recipeState by recipeViewModel.recipeState.collectAsState()

    val uploadProgress by recipeViewModel.uploadProgress.collectAsState()

    val profile by authViewModel.currentProfile.collectAsState()

    val isLoading = recipeState is RecipeState.Loading

    val errorMessage =
        (recipeState as? RecipeState.Error)?.message

    LaunchedEffect(recipeState) {

        if (recipeState is RecipeState.Success) {

            recipeViewModel.clearState()

            navController.popBackStack()

        }

    }


    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Upload Recipe",
                        color = Color.White
                    )

                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )

                    }

                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Pink
                )

            )

        },

        containerColor = Color(0xFFFFF5F8)

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            errorMessage?.let {

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFCDD2)
                    )

                ) {

                    Row(

                        modifier = Modifier.padding(12.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = Color.Red
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(it)

                    }

                }

            }

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(
                        2.dp,
                        if (recipeImageUri != null) Pink else Color.LightGray,
                        RoundedCornerShape(16.dp)
                    )
                    .clickable {

                        launcher.launch("image/*")

                    },

                contentAlignment = Alignment.Center

            ) {

                if (recipeImageUri != null) {

                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Pink,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(

                            recipeImageUri.toString(),

                            maxLines = 2,

                            overflow = TextOverflow.Ellipsis,

                            textAlign = TextAlign.Center

                        )

                    }

                } else {

                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Icon(

                            Icons.Default.AddAPhoto,

                            contentDescription = null,

                            tint = Pink,

                            modifier = Modifier.size(50.dp)

                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(

                            "Tap to Select Recipe Image",

                            color = Pink

                        )

                    }

                }

            }
            // Recipe Name
            OutlinedTextField(
                value = recipeName,
                onValueChange = {
                    recipeName = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Recipe Name")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.RestaurantMenu,
                        contentDescription = null,
                        tint = Pink
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )

            // Ingredients
            OutlinedTextField(
                value = ingredients,
                onValueChange = {
                    ingredients = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Ingredients")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = null,
                        tint = Pink
                    )
                },
                minLines = 5,
                shape = RoundedCornerShape(12.dp)
            )

            // Cooking Instructions
            OutlinedTextField(
                value = instructions,
                onValueChange = {
                    instructions = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Cooking Instructions")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = Pink
                    )
                },
                minLines = 7,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Recipe Category",
                style = MaterialTheme.typography.titleMedium,
                color = Pink
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                categories.forEach { cat ->

                    FilterChip(

                        selected = category == cat,

                        onClick = {
                            category = cat
                        },

                        label = {
                            Text(cat)
                        },

                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Pink,
                            selectedLabelColor = Color.White
                        )

                    )

                }

            }

            Spacer(modifier = Modifier.height(10.dp))

            if (isLoading && uploadProgress > 0f) {

                Column {

                    LinearProgressIndicator(
                        progress = { uploadProgress },
                        modifier = Modifier.fillMaxWidth(),
                        color = Pink
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Uploading Recipe... ${(uploadProgress * 100).toInt()}%",
                        color = Pink
                    )

                }

            }
            // Upload Button
            Button(
                onClick = {

                    recipeImageUri?.let { uri ->

                        recipeViewModel.uploadRecipe(
                            context = context,
                            recipeName = recipeName,
                            ingredients = ingredients,
                            instructions = instructions,
                            category = category,
                            imageUri = uri,
                            ownerName = profile?.fullName ?: "Anonymous"
                        )

                    }

                },

                enabled =
                    recipeName.isNotBlank() &&
                            ingredients.isNotBlank() &&
                            instructions.isNotBlank() &&
                            recipeImageUri != null &&
                            !isLoading,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(14.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink
                )

            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Upload Recipe",
                        color = Color.White
                    )

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

        }

    }

}