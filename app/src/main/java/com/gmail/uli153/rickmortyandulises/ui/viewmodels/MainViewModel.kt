package com.gmail.uli153.rickmortyandulises.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.domain.usecases.CharacterUseCases
import com.gmail.uli153.rickmortyandulises.domain.usecases.EpisodeUseCases
import com.gmail.uli153.rickmortyandulises.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterUseCases: CharacterUseCases,
    private val episodeUseCases: EpisodeUseCases
    ): ViewModel() {

    private val _characters: MutableStateFlow<PagingData<CharacterModel>> = MutableStateFlow(PagingData.from(emptyList()))
    val characters: StateFlow<PagingData<CharacterModel>> = _characters

    val nameFilter = MutableStateFlow("")
    val statusFilter: MutableStateFlow<CharacterStatus?> = MutableStateFlow(null)

    private val _selectedCharacter: MutableStateFlow<UIState<CharacterModel>> = MutableStateFlow(UIState.Loading)
    val selectedCharacter: StateFlow<UIState<CharacterModel>> = _selectedCharacter

    // when a character is selected (detail character), it triggers character's episodes fetch
    @OptIn(ExperimentalCoroutinesApi::class)
    val characterEpisodes: StateFlow<List<EpisodeModel?>> = selectedCharacter.flatMapLatest {
        val episodes = if (it is UIState.Success) {
            episodeUseCases.getEpisodesByIds(it.data.episodes)
        } else {
            flowOf()
        }
        episodes
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val relatedcharacterIds: StateFlow<List<Long>> = characterEpisodes.transform { episodes ->
        val selectedCharacterId = when (val s = selectedCharacter.value) {
            is UIState.Success -> s.data.id
            else -> null
        }
        val characterOcurrences = mutableMapOf<Long, Int>()
        episodes.filterNotNull().flatMap { episode -> episode.characters }.forEach { id ->
            characterOcurrences[id] = (characterOcurrences[id] ?: 0) + 1
        }
        val relatedCharacterIds = characterOcurrences.entries
            .filter { it.key != selectedCharacterId } // removed current selected character from related list
            .sortedByDescending { it.value } // sorted by ocurrences (more related character)
            .map { it.key }

        emit(relatedCharacterIds)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // when selected character's episodes is loaded, it triggers related characters fetch
    @OptIn(ExperimentalCoroutinesApi::class)
    val relatedcharacters: StateFlow<PagingData<CharacterModel>> = relatedcharacterIds.flatMapLatest { relatedCharacterIds ->
        characterUseCases.getPagedCharactersById(relatedCharacterIds)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.from(emptyList()))

    private var filtersJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val filters = combine(nameFilter, statusFilter) { name, status ->
                Filters(name, status)
            }
            // when filters changes, it triggers a character fetch
            filters.collectLatest {
                filtersJob?.cancel()
                val characters = characterUseCases.getAllCharacters(it.name, it.status).cachedIn(viewModelScope)
                filtersJob = viewModelScope.launch(Dispatchers.IO) {
                    characters.collectLatest {
                        _characters.value = it
                    }
                }
            }
        }
    }

    fun selectCharacter(id: Long) {
        _selectedCharacter.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val character = characterUseCases.getCharacterById(id)
            withContext(Dispatchers.Main) {
                if (character != null) {
                    _selectedCharacter.value = UIState.Success(character)
                } else {
                    _selectedCharacter.value = UIState.Error(IllegalArgumentException("Character not found"))
                }
            }
        }
    }
}

private data class Filters(val name: String, val status: CharacterStatus?)