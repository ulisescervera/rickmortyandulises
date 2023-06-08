package com.gmail.uli153.rickmortyandulises.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.gmail.uli153.rickmortyandulises.R
import com.gmail.uli153.rickmortyandulises.navigation.NavigationItem
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val items = NavigationItem.values()
    val title = items.find { it.route == navBackStackEntry?.destination?.route }?.title?.let { stringResource(id = it) } ?: ""
    val showBackButton = navBackStackEntry?.destination?.route != NavigationItem.Home.route

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = Dimens.navigationBarHorizontalMargin,
            end = Dimens.navigationBarHorizontalMargin,
            top = Dimens.bottomNavigationMarginBottom
        )
    ) {
        TopAppBar(
            navigationIcon = {
                if (showBackButton) {
                    Image(painter = rememberAsyncImagePainter(model = R.drawable.ic_back),
                        contentDescription = "navigate back",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.padding(vertical = 4.dp)
                            .clickable { navController.popBackStack() }
                    )
                } },
            title = {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.navigationBarHeight)
                .clip(RoundedCornerShape(Dimens.navigationBarHeight / 2))
        )
    }
}