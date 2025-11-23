package com.example.myprojects

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myprojects.coffeeshops.CoffeShopsScreen
import com.example.myprojects.myphotos.MyPhotosScreen
import com.example.myprojects.the_sun.TheSunScreen
import com.example.myprojects.ui.theme.MyProjectsTheme

data class ScreenItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyProjectsTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MyNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "portada",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("portada") { Portada() }

            composable("MyPhotos") { MyPhotosScreen() }
            composable("CoffeeShops") { CoffeShopsScreen() }
            composable("ElSol") { TheSunScreen() }
        }
    }
}

@Composable
fun Portada() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "MyProjects",
            fontSize = 40.sp)
    }
}

@Composable
fun MyNavigationBar(navController: NavHostController) {
    val items = listOf(
        ScreenItem("MyPhotos", "Photos", Icons.Filled.Star, Icons.Outlined.Star),
        ScreenItem("CoffeeShops", "Coffee", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
        ScreenItem("ElSol", "El Sol", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Remove the main menu from history
                        popUpTo("portada") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}