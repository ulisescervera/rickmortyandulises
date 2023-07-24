package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gmail.uli153.rickmortyandulises.R
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens

@Composable
fun CharacterListScreen(
    padding: PaddingValues,
    showFilters: State<Boolean>,
    characters: LazyPagingItems<CharacterModel>,
    nameFilter: State<String>,
    statusFilter: State<CharacterStatus?>,
    onQueryChanged: (String) -> Unit,
    onStateChanged: (CharacterStatus?) -> Unit,
    onCharacterClicked: (CharacterModel) -> Unit
) {
    val topPadding = Dimens.vMargin + padding.calculateTopPadding()
    val queryViewHeight = Dimens.textFieldHeight + Dimens.vMargin

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        val (list, filter) = createRefs()

        LazyVerticalGrid(
            contentPadding = PaddingValues(horizontal = Dimens.hMargin, vertical = topPadding + queryViewHeight),
            columns = GridCells.Adaptive(128.dp),
            modifier = Modifier.constrainAs(list) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            verticalArrangement = Arrangement.spacedBy(Dimens.rowVSpace),
            horizontalArrangement = Arrangement.spacedBy(Dimens.rowVSpace),
        ) {
            items(characters.itemCount, key = { characters[it]?.id ?: 0 }) {
                val character = characters[it]
                if (character != null) {
                    CharacterListItem(character, onCharacterClicked)
                }
            }
        }

        AnimatedVisibility(
            visible = showFilters.value,
            modifier = Modifier.constrainAs(filter) {
            start.linkTo(parent.start, Dimens.hMargin)
            top.linkTo(parent.top, topPadding)
            end.linkTo(parent.end, Dimens.hMargin)
            width = Dimension.fillToConstraints
            height = Dimension.value(Dimens.textFieldHeight)
        }) {
            OutlinedTextField(
                value = nameFilter.value,
                onValueChange = onQueryChanged,
                placeholder = { Text(stringResource(id = R.string.search_by_name)) },
                singleLine = true,
                shape = RoundedCornerShape(Dimens.textFieldHeight / 2),
                modifier = Modifier.fillMaxSize(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListItem(character: CharacterModel, onCharacterClicked: (CharacterModel) -> Unit) {
    val imageLoader = ImageRequest.Builder(LocalContext.current)
        .data(character.image)
        .crossfade(true)
        .crossfade(250)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.8f),
    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    onClick = {onCharacterClicked(character) }
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)) {
            AsyncImage(model = imageLoader,
                contentDescription = "${character.name} image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = character.name, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp))
        Text(text = "State: ${character.status.name}", modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp))
    }
}