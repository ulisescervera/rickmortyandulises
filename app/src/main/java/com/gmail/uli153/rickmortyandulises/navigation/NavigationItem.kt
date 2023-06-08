package com.gmail.uli153.rickmortyandulises.navigation

import com.gmail.uli153.rickmortyandulises.R

enum class NavigationItem(val route: String, val title: Int) {
    Home("home", R.string.title_character_list),
    Detail("charts", R.string.title_character_detail)
}