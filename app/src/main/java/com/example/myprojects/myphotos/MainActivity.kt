package com.example.myprojects.myphotos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MyPhotosScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val urls = listOf(
                "https://i.pinimg.com/736x/5e/7c/ab/5e7cab01fc4d967d0c1a075b32419806.jpg",
                "https://i.pinimg.com/736x/ba/ea/73/baea738c0f0afe83c1d42c4b406d3f8e.jpg",
                "https://i.pinimg.com/736x/ef/2c/ca/ef2cca1cc087de9d46d5e9a92af27195.jpg",
                "https://i.pinimg.com/736x/df/af/c6/dfafc62114d7aa03528c261d19a4e939.jpg",
                "https://i.pinimg.com/736x/ec/60/33/ec603338930c4069be7e4784a6023f23.jpg",
                "https://i.pinimg.com/736x/be/0f/d0/be0fd0e73a3b907fd5f093179ee61b19.jpg",
                "https://i.pinimg.com/736x/17/d0/85/17d085441ad7aeacaab59f5bc72744b9.jpg"
            )

            ImageCarouselWithPreview(urls)
        }
    }
}

@Composable
fun ImageCarouselWithPreview(imageUrls: List<String>) {

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(imageUrls) { imageUrl ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable { selectedImageUrl = imageUrl }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedImageUrl?.let { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            )
        } ?: Text("Tap an image above to preview it",
            fontSize = 20.sp)
    }
}
