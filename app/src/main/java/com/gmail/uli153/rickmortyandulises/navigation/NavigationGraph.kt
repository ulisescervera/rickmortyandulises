package com.gmail.uli153.rickmortyandulises.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.ui.screens.CharacterDetailScreen
import com.gmail.uli153.rickmortyandulises.ui.screens.CharacterListScreen
import com.gmail.uli153.rickmortyandulises.ui.screens.EpisodeListScreen
import com.gmail.uli153.rickmortyandulises.ui.screens.LocationListScreen
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    padding: PaddingValues,
    mainViewModel: MainViewModel,
    showFilters: State<Boolean>
) {
    val characters = mainViewModel.characters.collectAsLazyPagingItems()
    val nameFilter = mainViewModel.nameFilter.collectAsState()
    val statusFilter = mainViewModel.statusFilter.collectAsState()
    val selectedCharacter = mainViewModel.selectedCharacter.collectAsState()
    val characterEpisodes = mainViewModel.characterEpisodes.collectAsState(initial = emptyList())
    val relatedCharacters = mainViewModel.relatedcharacters.collectAsLazyPagingItems()

    val episodes = mainViewModel.episodes.collectAsLazyPagingItems()

    val onQueryChanged: (String) -> Unit = {
        mainViewModel.nameFilter.value = it
    }
    val onStateChanged: (CharacterStatus?) -> Unit = {
        mainViewModel.statusFilter.value = it
    }
    val onCharacterClicked: (CharacterModel) -> Unit = {
        mainViewModel.selectCharacter(it.id)
        navController.navigate(NavigationItem.Detail.route)
    }
    val onCharacterSelected: (CharacterModel) -> Unit = {
        mainViewModel.selectCharacter(it.id)
    }

    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            CharacterListScreen(padding, showFilters, characters, nameFilter, statusFilter, onQueryChanged, onStateChanged, onCharacterClicked)
        }

        composable(NavigationItem.Episodes.route) {
            EpisodeListScreen(episodes)
        }

        composable(NavigationItem.Locations.route) {
            LocationListScreen()
        }

        composable(NavigationItem.Detail.route) {
            CharacterDetailScreen(padding, selectedCharacter.value, relatedCharacters, characterEpisodes.value, onCharacterSelected)
        }
    }
}