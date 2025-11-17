package com.example.coffeeshops

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myprojects.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(modifier: Modifier = Modifier, navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coffee Shops") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Compartir") },
                            onClick = { showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Album") },
                            onClick = { showMenu = false }
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        val coffeeShops = listOf(
            CoffeeShop("Antico Caffé Greco", "St. Italy, Rome", R.drawable.images),
            CoffeeShop("Coffee Room", "St. Germany, Berlin", R.drawable.images1),
            CoffeeShop("Coffee Ibiza", "St. Colon, Madrid", R.drawable.images2),
            CoffeeShop("Pudding Coffee Shop", "St. Diagonal, Barcelona",R.drawable.images3),
            CoffeeShop("L'Express", "St. Picadilly Circus, London", R.drawable.images4),
            CoffeeShop("Coffee Corner", "St. Àngel Guimerà, Valencia", R.drawable.images5),
            CoffeeShop("Sweet Cup", "St. Kinkerstraat, Amsterdam", R.drawable.images6)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(coffeeShops) { shop ->
                CoffeeShopCard(shop) {
                    navController.navigate("cafeteria/${shop.name}")
                }
            }
        }
    }
}

@Composable
fun CoffeeShopCard(shop: CoffeeShop, onClick: () -> Unit) {
    var rating by remember { mutableStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = shop.imageRes),
                contentDescription = shop.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(shop.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(shop.address, color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Row {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating ${index + 1}",
                        tint = if (index < rating) Color(0xFFFFD700) else Color(0xFF4F4E4A),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { rating = index + 1 }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { /* sin funcionalidad */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Reservar")
            }
        }
    }
}

data class CoffeeShop(
    val name: String,
    val address: String,
    val imageRes: Int,
    val rating: Int = 0
)
