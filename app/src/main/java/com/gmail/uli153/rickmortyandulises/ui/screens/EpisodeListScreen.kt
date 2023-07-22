/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens
import com.gmail.uli153.rickmortyandulises.ui.views.RelatedCharacterCell
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeListScreen(
    padding: PaddingValues,
    episodes: LazyPagingItems<EpisodeModel>,
    getCharacters: (EpisodeModel) -> Flow<PagingData<CharacterModel>>,
    onCharacterClicked: (CharacterModel) -> Unit
) {
    val topPadding = Dimens.vMargin + padding.calculateTopPadding() + Dimens.navigationBarHorizontalMargin
    val bottomMargin = padding.calculateBottomPadding()
    val expandedIndices = remember { mutableStateListOf<Boolean>() }

    val diff = episodes.itemCount - expandedIndices.size
    if (diff > 0) {
        val tmp = expandedIndices.toList()
        for ( i in 0 until diff) expandedIndices.add(false)
    }

    val toggleExpanded: (Int) -> Unit = {
        expandedIndices[it] = expandedIndices[it].not()
    }

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        val (list) = createRefs()
        LazyColumn(
            modifier = Modifier.constrainAs(list) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, bottomMargin)
            },
            contentPadding = PaddingValues(top = topPadding, start = Dimens.hMargin, end = Dimens.hMargin, bottom = Dimens.vMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.rowVSpace)
        ) {
            items(episodes.itemCount) { index ->
                val episode = episodes[index]
                val isExpanded = expandedIndices[index]

                if (episode != null) {
                        ElevatedCard(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                        onClick = { toggleExpanded(index) }
                    ) {
                        ConstraintLayout(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.rowVPadding, horizontal = Dimens.rowHPadding)
                        ) {
                            val (name, list) = createRefs()

                            Text(text = episode.name, modifier = Modifier.constrainAs(name) {
                                start.linkTo(parent.start, Dimens.rowHPadding)
                                top.linkTo(parent.top, Dimens.rowVPadding)
                                end.linkTo(parent.end, Dimens.rowHPadding)
                            })

                            if (isExpanded) {
                                val characters = getCharacters(episode).collectAsLazyPagingItems()
                                LazyRow(
                                    modifier = Modifier.constrainAs(list) {
                                        start.linkTo(parent.start)
                                        top.linkTo(name.bottom, 10.dp)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom, Dimens.rowVPadding)
                                        height = Dimension.value(96.dp)
                                        width = Dimension.fillToConstraints
                                    },
                                    horizontalArrangement = Arrangement.spacedBy(Dimens.rowVSpace),
                                    contentPadding = PaddingValues(horizontal = Dimens.rowHPadding)
                                ) {
                                    items(characters.itemCount, key = { characters[it]?.id ?: 0 }) {
                                        val character = characters[it]
                                        if (character != null) {
                                            RelatedCharacterCell(character, onCharacterClicked)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    //todo shimmer?
                }
            }
        }
    }
}