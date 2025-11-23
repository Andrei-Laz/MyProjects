package com.example.myprojects.the_sun

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.myprojects.R
import com.example.myprojects.the_sun.data.DataSource
import com.example.myprojects.the_sun.model.Solar_Image
import kotlinx.coroutines.CoroutineScope

@Composable
fun TheSunScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedScreen by remember { mutableStateOf("home") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SunDrawerContent(
                onHome = {
                    selectedScreen = "home"
                    scope.launch { drawerState.close() }
                },
                onDownload = {
                    selectedScreen = "download"
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        when (selectedScreen) {
            "home" -> SolarApp(drawerState, scope)
            "download" -> DownloadScreen(drawerState, scope)
        }
    }
}

@Composable
fun SunDrawerContent(
    onHome: () -> Unit,
    onDownload: () -> Unit
) {
    ModalDrawerSheet {

        Image(
            painter = painterResource(id = R.drawable.portada),
            contentDescription = "Logo Sol",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AssistChip(
            onClick = onHome,
            label = { Text("Home") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )

        AssistChip(
            onClick = onDownload,
            label = { Text("Download more info") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )

        AssistChip(
            onClick = { },
            label = { Text("Email") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolarApp(
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var solarImages by remember { mutableStateOf(DataSource.solarImages.toMutableList()) }
    var favCount by remember { mutableStateOf(0) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        topBar = {
            TopAppBar(
                title = { Text("The Sun") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "menu")
                    }
                }
            )
        },

        bottomBar = {
            BottomAppBar(
                actions = {
                    BadgedBox(
                        badge = {
                            if (favCount > 0) Badge { Text(favCount.toString()) }
                        }
                    ) {
                        IconButton(onClick = { favCount++ }) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            )
        }
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(innerPadding).padding(8.dp)
        ) {
            items(solarImages) { solarImage ->
                val imageNameRes = stringResource(id = solarImage.name)

                SolarImageCard(
                    solarImage = solarImage,
                    imageName = imageNameRes,
                    onCardClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Imagen: $imageNameRes")
                        }
                    },
                    onCopy = {
                        solarImages = (solarImages + solarImage.copy()).toMutableList()
                    },
                    onDelete = {
                        solarImages = solarImages.toMutableList().also { it.remove(solarImage) }
                    }
                )
            }
        }
    }
}

@Composable
fun SolarImageCard(
    solarImage: Solar_Image,
    imageName: String,
    onCardClick: () -> Unit,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .size(250.dp)
            .padding(8.dp)
            .clickable { onCardClick() }
    ) {
        Column {
            Image(
                painter = painterResource(id = solarImage.imageRes),
                contentDescription = null,
                modifier = modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = imageName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .weight(1f)
                )

                var expanded by remember { mutableStateOf(false) }

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(Icons.Filled.Add, contentDescription = null)
                            },
                            text = { Text("Copiar") },
                            onClick = {
                                expanded = false
                                onCopy()
                            }
                        )
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(Icons.Filled.Delete, contentDescription = null)
                            },
                            text = { Text("Eliminar") },
                            onClick = {
                                expanded = false
                                onDelete()
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    var isDownloading by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    if (isDownloading) {
        LaunchedEffect(Unit) {
            progress = 0f
            val totalSteps = 100
            val delayMillis = 30L

            repeat(totalSteps) {
                kotlinx.coroutines.delay(delayMillis)
                progress += 1f / totalSteps
            }
            isDownloading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Download") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isDownloading) {
                CircularProgressIndicator(
                    progress = progress,
                    strokeWidth = 8.dp,
                    modifier = Modifier.size(120.dp)
                )

                Spacer(Modifier.height(20.dp))

                Text("${(progress * 100).toInt()}% descargado")
            }

            Button(
                onClick = {
                    if (!isDownloading) {
                        isDownloading = true
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Download more info")
            }
        }
    }
}
