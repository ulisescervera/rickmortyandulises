package com.gmail.uli153.rickmortyandulises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel
import com.gmail.uli153.rickmortyandulises.utils.UIState

@Composable
fun CharacterDetailScreen(padding: PaddingValues, mainViewModel: MainViewModel) {
    //todo remove error state, instead use SharedFlow...
    val character = when(val c = mainViewModel.selectedCharacter.collectAsState().value) {
        UIState.Loading -> null
        is UIState.Error -> null
        is UIState.Success -> c.data
    }

    val characterEpisodes = mainViewModel.characterEpisodes.collectAsState()

    ConstraintLayout(modifier = Modifier
        .fillMaxSize(1f)
        .padding(padding)
        .background(MaterialTheme.colorScheme.background)
    ) {
        val (image, name, state, episodeList) = createRefs()
        if (character != null) {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .crossfade(250)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
                contentDescription = "${character.name} image",
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.secondary)
                    .constrainAs(image) {
                        start.linkTo(parent.start, Dimens.hMargin)
                        top.linkTo(parent.top, Dimens.vMargin)
                    }
            )

            Text(text = character.name, fontSize = 22.sp, modifier = Modifier.constrainAs(name) {
                start.linkTo(image.end, 12.dp)
                top.linkTo(image.top)
                end.linkTo(parent.end, Dimens.vMargin)
                width = Dimension.fillToConstraints
            })
            Text(text = "State: ${character.status.name}", modifier = Modifier.constrainAs(state) {
                start.linkTo(image.end, 12.dp)
                top.linkTo(name.bottom, 12.dp)
                end.linkTo(parent.end, Dimens.vMargin)
                width = Dimension.fillToConstraints
            })

            LazyRow(modifier = Modifier.constrainAs(episodeList) {
                start.linkTo(parent.start, Dimens.hMargin)
                top.linkTo(image.bottom)
                end.linkTo(parent.end, Dimens.hMargin)
                height = Dimension.value(60.dp)
            }) {
                items(characterEpisodes.value.size) { index ->
                    EpisodeCell(characterEpisodes.value[index])
                }
            }
        } else {
            //todo shimmer
        }
    }
}

@Composable
private fun EpisodeCell(episode: EpisodeModel?) {
    Box(modifier = Modifier.aspectRatio(1f).height(64.dp)) {
        Text(text = episode?.name ?: "null")
    }
}