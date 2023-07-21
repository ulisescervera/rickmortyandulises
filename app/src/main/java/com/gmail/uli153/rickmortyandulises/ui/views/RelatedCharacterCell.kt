/**
 * Created by Ulises on 21/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterModel

@Composable
fun RelatedCharacterCell(character: CharacterModel, onCharacterSelected: (CharacterModel) -> Unit) {
    val imageLoader = ImageRequest.Builder(LocalContext.current)
        .data(character.image)
        .crossfade(true)
        .crossfade(250)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()

    ElevatedCard(shape = ShapeDefaults.ExtraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = { onCharacterSelected(character) })
    ) {
        AsyncImage(model = imageLoader,
            contentDescription = "${character.name} image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }
}