package com.gmail.uli153.rickmortyandulises.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.ui.screens.CharacterDetailScreen
import com.gmail.uli153.rickmortyandulises.ui.screens.CharacterListScreen
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    padding: PaddingValues,
    mainViewModel: MainViewModel,
) {
    val onCharacterClicked: (CharacterModel) -> Unit = {
        mainViewModel.selectCharacter(it.id)
        navController.navigate(NavigationItem.Detail.route)
    }
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            CharacterListScreen(mainViewModel, onCharacterClicked)
        }

        composable(NavigationItem.Detail.route) {
            CharacterDetailScreen()
        }
    }
}