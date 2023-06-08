package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel

@Composable
fun CharacterListScreen(
    padding: PaddingValues,
    viewModel: MainViewModel,
    onCharacterClicked: (CharacterModel) -> Unit
) {
    val topPadding = Dimens.vMargin + padding.calculateTopPadding()
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val nameFilter = viewModel.nameFilter.collectAsState()
    val statusFilter = viewModel.statusFilter.collectAsState()
    val onQueryChanged: (String) -> Unit = {
        viewModel.nameFilter.value = it
    }
    val onStateChanged: (CharacterStatus?) -> Unit = {
        viewModel.statusFilter.value = it
    }
    val queryViewHeight = 64.dp
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val shape = RoundedCornerShape(4.dp)
    val borderModifier = if (isFocused.value) Modifier.border(1.dp, MaterialTheme.colorScheme.secondary, shape) else Modifier

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = Dimens.hMargin, vertical = topPadding + queryViewHeight),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = characters, key = { it.id }) { character ->
                if (character != null) {
                    CharacterListItem(character, onCharacterClicked)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
//        CharacterFilter(nameFilter, statusFilter, onQueryChanged, onStateChanged,
//            Modifier.fillMaxWidth().height(200.dp).padding(start = Dimens.hMargin, top = 0.dp, end = Dimens.hMargin)
//        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimens.hMargin, top = topPadding, end = Dimens.hMargin)
        ) {
            TextField(
                value = nameFilter.value,
                onValueChange = onQueryChanged,
                placeholder = { Text("Busca por nombre") },
                interactionSource = interactionSource,
                singleLine = true,
                modifier = borderModifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, shape)
            )
        }
    }
}

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
        .fillMaxHeight(0.8f)
        .background(MaterialTheme.colorScheme.surface)
        .clickable(onClick = { onCharacterClicked(character) })
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