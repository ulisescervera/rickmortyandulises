package com.gmail.uli153.rickmortyandulises.navigation

import com.gmail.uli153.rickmortyandulises.R

enum class NavigationItem(val route: String, val title: Int, val icon: Int) {
    Home("home", R.string.title_character_list, R.drawable.ic_back),
    Detail("charts", R.string.title_character_detail, R.drawable.ic_back),
    Episodes("episodes", R.string.title_episodes, R.drawable.ic_back),
    Locations("locations", R.string.title_locations, R.drawable.ic_back);

    companion object {
        val mainNavigationItemRoutes: Array<NavigationItem> get() = arrayOf(Home, Episodes, Locations)
    }
}