package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.navigation.NavigationGraph
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel
import com.gmail.uli153.rickmortyandulises.ui.views.TopBar

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar(navController) },
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxWidth(1f)
            .background(MaterialTheme.colorScheme.background)
        ) {
            NavigationGraph(navController, padding, mainViewModel)
        }
    }
}