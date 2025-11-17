package com.example.coffeeshops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coffeeshops.ui.theme.CoffeeShopsTheme

@Composable
fun CoffeShopsScreen() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "menuPrincipal",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("menuPrincipal") {
                MainMenu(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }

            composable(
                route = "cafeteria/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: "Cafetería"
                CafeteriaScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    cafeName = name
                )
            }
        }
    }
}