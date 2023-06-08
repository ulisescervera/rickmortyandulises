package com.gmail.uli153.rickmortyandulises.utils

sealed class UIState<out T> {
    object Loading: UIState<Nothing>()
    data class Success<T>(val data: T): UIState<T>()
    data class Error<T>(val error: Throwable): UIState<T>()
}
