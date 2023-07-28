package com.gmail.uli153.rickmortyandulises.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.gmail.uli153.rickmortyandulises.ui.views.BottomBar
import com.gmail.uli153.rickmortyandulises.ui.views.TopBar
import kotlinx.coroutines.flow.Flow

@Composable
fun NavigationGraph(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

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
    val onCharacterSelected: (CharacterModel) -> Unit = {
        mainViewModel.selectCharacter(it.id)
        navigateTo(NavigationItem.Detail, navController)
    }

    val getEpisodeCharacters: (EpisodeModel) -> Flow<PagingData<CharacterModel>> = {
        mainViewModel.getCharacterInEpisode(it)
    }

    val getLocationCharacters: (LocationModel) -> Flow<PagingData<CharacterModel>> = {
        mainViewModel.getCharacterInLocation(it)
    }

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
        NavHost(navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            composable(NavigationItem.Home.route) {
                CharacterListScreen(padding, showFilters, characters, nameFilter, statusFilter, onQueryChanged, onStateChanged, onCharacterSelected)
            }

            composable(NavigationItem.Episodes.route) {
                EpisodeListScreen(padding, episodes, getEpisodeCharacters, onCharacterSelected)
            }

            composable(NavigationItem.Locations.route) {
                LocationListScreen(padding, locations, getLocationCharacters, onCharacterSelected)
            }

            composable(NavigationItem.Detail.route) {
                CharacterDetailScreen(padding, selectedCharacter.value, relatedCharacters, characterEpisodes.value, onCharacterSelected)
            }
        }
    }
}

private fun navigateTo(item: NavigationItem, navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: return
    if (item.route == currentRoute) return
    val exitApp = when(item) {
        NavigationItem.Home -> true
        NavigationItem.Episodes -> true
        NavigationItem.Locations -> true
        NavigationItem.Detail -> false
    }

    navController.navigate(item.route) {
        popUpTo(currentRoute) {
            inclusive = exitApp
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}