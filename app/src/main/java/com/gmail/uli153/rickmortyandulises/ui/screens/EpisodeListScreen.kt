/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens
import com.gmail.uli153.rickmortyandulises.ui.views.RelatedCharacterCell
import kotlinx.coroutines.flow.Flow

@Composable
fun EpisodeListScreen(
    padding: PaddingValues,
    episodes: LazyPagingItems<EpisodeModel>,
    getCharacters: (EpisodeModel) -> Flow<PagingData<CharacterModel>>,
    onCharacterClicked: (CharacterModel) -> Unit
) {
    val topPadding = Dimens.vMargin + padding.calculateTopPadding() + Dimens.navigationBarHorizontalMargin
    val bottomMargin = padding.calculateBottomPadding()

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
            items(episodes.itemCount, key = { episodes[it]?.id ?: 0 }) { index ->
                val episode = episodes[index]
                if (episode != null) {
                    EpisodeCell(episode, getCharacters, onCharacterClicked)
                } else {
                    //todo shimmer?
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodeCell(
    episode: EpisodeModel,
    getCharacters: (EpisodeModel) -> Flow<PagingData<CharacterModel>>,
    onCharacterClicked: (CharacterModel) -> Unit,
) {
    val isExpanded = rememberSaveable {
        mutableStateOf(false)
    }

    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = { isExpanded.value = !isExpanded.value }
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

            if (isExpanded.value) {
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
}