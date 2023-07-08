package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gmail.uli153.rickmortyandulises.navigation.NavigationGraph
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel
import com.gmail.uli153.rickmortyandulises.ui.views.TopBar

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val showFilters: MutableState<Boolean> = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopBar(navController, toggleFiltersVisibility = { showFilters.value = showFilters.value.not() } )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxWidth(1f)
            .background(MaterialTheme.colorScheme.background)
        ) {
            NavigationGraph(navController, padding, mainViewModel, showFilters)
        }
    }
}