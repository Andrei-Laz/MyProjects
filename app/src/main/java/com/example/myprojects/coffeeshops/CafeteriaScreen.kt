package com.example.coffeeshops

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CafeteriaScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    cafeName: String
) {
    val comments = remember {
        mutableStateListOf(
            "Excelente café y ambiente.",
            "El capuchino estaba delicioso.",
            "Muy buena atención del personal.",
            "Espacio tranquilo para estudiar.",
            "Un poco caro, pero vale la pena.",
            "Excelente café y ambiente.",
            "El capuchino estaba delicioso.",
            "Muy buena atención del personal.",
            "Espacio tranquilo para estudiar.",
            "Un poco caro, pero vale la pena.",
            "Excelente café y ambiente.",
            "El capuchino estaba delicioso.",
            "Muy buena atención del personal.",
            "Espacio tranquilo para estudiar.",
            "Un poco caro, pero vale la pena.",
            "Excelente café y ambiente.",
            "El capuchino estaba delicioso.",
            "Muy buena atención del personal.",
            "Espacio tranquilo para estudiar.",
            "Un poco caro, pero vale la pena.",
            "Excelente café y ambiente.",
            "El capuchino estaba delicioso.",
            "Muy buena atención del personal.",
            "Espacio tranquilo para estudiar.",
            "Un poco caro, pero vale la pena."
        )
    }

    var showMenu by remember { mutableStateOf(false) }
    val gridState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()
    var showAddButton by remember { mutableStateOf(true) }

    // Detectar dirección del scroll
    LaunchedEffect(gridState) {
        var lastScrollOffset = 0
        snapshotFlow { gridState.firstVisibleItemScrollOffset }
            .collect { offset ->
                showAddButton = offset > lastScrollOffset
                lastScrollOffset = offset
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coffee Shops")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Opciones") },
                            onClick = { showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Salir") },
                            onClick = { navController.navigateUp() }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = showAddButton) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                comments.add("Nuevo comentario agregado.")
                                gridState.animateScrollToItem(0)
                            }
                        },
                        icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                        text = { Text("Add new comment") }
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->

        Text(text = cafeName)

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            state = gridState,
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(comments) { comment ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Text(
                            text = comment,
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
