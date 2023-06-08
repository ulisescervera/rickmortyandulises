package com.gmail.uli153.rickmortyandulises.ui.screens

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens

import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel

@Composable
fun CharacterListScreen(
    viewModel: MainViewModel,
    onCharacterClicked: (CharacterModel) -> Unit
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = Dimens.hMargin, vertical = Dimens.vMargin),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = characters, key = { it.id }) { character ->
                if (character != null) {
                    CharacterListItem(character, onCharacterClicked)
                }
            }
        }
    }
}

@Composable
private fun CharacterListItem(character: CharacterModel, onCharacterClicked: (CharacterModel) -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.8f)
        .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(model = character.image,
                contentDescription = "${character.name} image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = character.name, modifier = Modifier.fillMaxWidth())
    }
}