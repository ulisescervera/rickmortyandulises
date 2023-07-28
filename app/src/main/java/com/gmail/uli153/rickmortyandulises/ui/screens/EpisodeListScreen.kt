/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmail.uli153.rickmortyandulises.R
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
    val listPadding = PaddingValues(
        top = Dimens.vMargin + padding.calculateTopPadding(),
        start = Dimens.hMargin,
        end = Dimens.hMargin,
        bottom = padding.calculateBottomPadding() + Dimens.vMargin
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = listPadding,
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
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.rowHPadding, vertical = Dimens.rowVPadding)
        ) {
            Text(text = "#${String.format("%02d", episode.ordinalNumber)} ${episode.name}", modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(visible = isExpanded.value) {
                val characters = getCharacters(episode).collectAsLazyPagingItems()
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp),
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