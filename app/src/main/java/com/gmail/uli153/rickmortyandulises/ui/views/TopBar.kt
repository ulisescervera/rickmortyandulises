package com.gmail.uli153.rickmortyandulises.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.gmail.uli153.rickmortyandulises.R
import com.gmail.uli153.rickmortyandulises.navigation.NavigationItem
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    toggleFiltersVisibility: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val items = NavigationItem.values()
    val title = items.find { it.route == navBackStackEntry?.destination?.route }?.title?.let { stringResource(id = it) } ?: ""
    val isHomeRoute = navBackStackEntry?.destination?.route == NavigationItem.Home.route

    val iconWidth = 32.dp
    val iconMargin = 8.dp

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
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(iconWidth)
                    .clickable { navController.popBackStack() }) {
                    if (!isHomeRoute) {
                        Image(
                            painter = rememberAsyncImagePainter(model = R.drawable.ic_back),
                            contentDescription = "navigate back",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier
                                .width(iconWidth)
                                .fillMaxHeight()
                                .padding(iconMargin / 2)
                        )
                    }
                }
            },
            title = {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (text, filterIcon) = createRefs()

                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.constrainAs(text) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end, iconWidth + iconMargin)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.wrapContent
                            width = Dimension.fillToConstraints
                        }
                    )

                    AnimatedVisibility(
                        visible = isHomeRoute,
                        modifier = Modifier
                            .padding(iconMargin / 2)
                            .clickable(onClick = toggleFiltersVisibility)
                            .constrainAs(filterIcon) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                                height = Dimension.fillToConstraints
                                width = Dimension.value(iconWidth + iconMargin)
                            }
                    ) {
                        Icon(painter = rememberAsyncImagePainter(R.drawable.ic_filters),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
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