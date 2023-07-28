package com.gmail.uli153.rickmortyandulises.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.domain.models.LocationModel
import com.gmail.uli153.rickmortyandulises.ui.screens.CharacterDetailScreen
import com.gmail.uli153.rickmortyandulises.ui.screens.CharacterListScreen
import com.gmail.uli153.rickmortyandulises.ui.screens.EpisodeListScreen
import com.gmail.uli153.rickmortyandulises.ui.screens.LocationListScreen
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel
import kotlinx.coroutines.flow.Flow

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

    val locations = mainViewModel.locations.collectAsLazyPagingItems()

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
        navController.navigate(NavigationItem.Detail.route)
    }

    val getEpisodeCharacters: (EpisodeModel) -> Flow<PagingData<CharacterModel>> = {
        mainViewModel.getCharacterInEpisode(it)
    }

    val getLocationCharacters: (LocationModel) -> Flow<PagingData<CharacterModel>> = {
        mainViewModel.getCharacterInLocation(it)
    }

    NavHost(navController,
        startDestination = NavigationItem.Home.route,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        composable(NavigationItem.Home.route) {
            CharacterListScreen(padding, showFilters, characters, nameFilter, statusFilter, onQueryChanged, onStateChanged, onCharacterClicked)
        }

        composable(NavigationItem.Episodes.route) {
            EpisodeListScreen(padding, episodes, getEpisodeCharacters, onCharacterClicked)
        }

        composable(NavigationItem.Locations.route) {
            LocationListScreen(padding, locations, getLocationCharacters, onCharacterSelected)
        }

        composable(NavigationItem.Detail.route) {
            CharacterDetailScreen(padding, selectedCharacter.value, relatedCharacters, characterEpisodes.value, onCharacterSelected)
        }
    }
}