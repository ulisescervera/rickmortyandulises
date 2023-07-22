package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gmail.uli153.rickmortyandulises.navigation.NavigationGraph
import com.gmail.uli153.rickmortyandulises.navigation.NavigationItem
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel
import com.gmail.uli153.rickmortyandulises.ui.views.BottomBar
import com.gmail.uli153.rickmortyandulises.ui.views.TopBar

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val showFilters: MutableState<Boolean> = remember { mutableStateOf(true) }
    val onToggleFilter = { showFilters.value = showFilters.value.not() }
    val onNavigateToItem: (NavigationItem) -> Unit = { navigateTo(it, navController) }

    Scaffold(
        topBar = {
            TopBar(navController, onToggleFilter)
        },
        bottomBar = {
            BottomBar(navController, onNavigateToItem)
        }
    ) { padding ->
        NavigationGraph(navController, padding, mainViewModel, showFilters)
    }
}

private fun navigateTo(item: NavigationItem, navController: NavHostController) {
    val popUpRoute = navController.currentBackStackEntry?.destination?.route ?: return
    navController.navigate(item.route) {
        popUpTo(popUpRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}