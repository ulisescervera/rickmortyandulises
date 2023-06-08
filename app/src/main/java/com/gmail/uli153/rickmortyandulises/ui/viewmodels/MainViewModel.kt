package com.gmail.uli153.rickmortyandulises.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.usecases.CharacterUseCases
import com.gmail.uli153.rickmortyandulises.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val characterUseCases: CharacterUseCases): ViewModel() {

    private val _characters: MutableStateFlow<PagingData<CharacterModel>> = MutableStateFlow(PagingData.from(emptyList()))
    val characters: StateFlow<PagingData<CharacterModel>> = _characters

    private val nameFilter = MutableStateFlow("")
    private val statusFilter: MutableStateFlow<CharacterStatus?> = MutableStateFlow(null)

    private val _selectedCharacter: MutableStateFlow<UIState<CharacterModel>> = MutableStateFlow(UIState.Loading)
    val selectedCharacter: StateFlow<UIState<CharacterModel>> = _selectedCharacter

    private var filtersJob: Job? = null

    init {
        viewModelScope.launch {
            val filters = combine(nameFilter, statusFilter) { name, status ->
                Filters(name, status)
            }
            filters.collectLatest {
                filtersJob?.cancel()
                val characters = characterUseCases.getAllCharacters(it.name, it.status).cachedIn(viewModelScope)
                filtersJob = viewModelScope.launch(Dispatchers.Main) {
                    characters.collectLatest {
                        _characters.value = it
                    }
                }
            }
        }
    }

    fun selectCharacter(id: Long) {
        _selectedCharacter.value = UIState.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val character = characterUseCases.getCharacterById(id)
            if (character != null) {
                _selectedCharacter.value = UIState.Success(character)
            } else {
                _selectedCharacter.value = UIState.Error(IllegalArgumentException("Character not found"))
            }
        }
    }
}

private data class Filters(val name: String, val status: CharacterStatus?)