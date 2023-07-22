/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.views

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gmail.uli153.rickmortyandulises.navigation.NavigationItem
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens
import com.gmail.uli153.rickmortyandulises.ui.theme.disabled

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val items = NavigationItem.mainNavigationItemRoutes

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.navigationBarHeight)
            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val onPrimary = MaterialTheme.colorScheme.onPrimary
        val colors = NavigationBarItemDefaults.colors(
            selectedIconColor = onPrimary,
            unselectedIconColor = onPrimary.disabled(),
            indicatorColor = MaterialTheme.colorScheme.primary,
        )
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon),
                    contentDescription = stringResource(id = item.title),
                    modifier = Modifier.fillMaxHeight(0.65f)
                )},
                label = null,
                selected = item.route == currentRoute,
                colors = colors,
                onClick = { onItemClicked(item, navController) }
            )
        }
    }
}

private fun onItemClicked(item: NavigationItem, navController: NavHostController) {
    navController.navigate(item.route) {
        navController.graph.startDestinationRoute?.let { start ->
            popUpTo(start) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}