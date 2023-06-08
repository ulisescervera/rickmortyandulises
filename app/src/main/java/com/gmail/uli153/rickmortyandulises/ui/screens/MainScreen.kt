package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gmail.uli153.rickmortyandulises.navigation.NavigationGraph
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    Scaffold(
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxWidth(1f)
        ) {
            NavigationGraph(navController, padding, mainViewModel)
        }
    }
}