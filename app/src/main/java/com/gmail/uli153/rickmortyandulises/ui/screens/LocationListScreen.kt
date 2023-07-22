/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.gmail.uli153.rickmortyandulises.domain.models.LocationModel
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens

@Composable
fun LocationListScreen(
    padding: PaddingValues,
    locations: LazyPagingItems<LocationModel>,
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        val (list) = createRefs()

        LazyColumn(
            modifier = Modifier.constrainAs(list) {
                start.linkTo(parent.start)
                top.linkTo(parent.top, padding.calculateTopPadding())
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, padding.calculateBottomPadding())
            },
            contentPadding = PaddingValues(top = Dimens.vMargin, start = Dimens.hMargin, end = Dimens.hMargin, bottom = Dimens.vMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.rowVSpace)
        ) {
            items(locations.itemCount) {
                val location = locations[it]

                if (location != null) {
                    ElevatedCard(modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(text = location.name, modifier = Modifier.fillMaxWidth())
                    }
                } else {
                    //todo shimmer?
                }
            }
        }
    }
}