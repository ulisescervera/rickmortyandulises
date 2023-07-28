/**
 * Created by Ulises on 18/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.gmail.uli153.rickmortyandulises.R
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens
import com.gmail.uli153.rickmortyandulises.utils.episodesMock

@Composable
fun EpisodeListDialog(episodes: List<EpisodeModel?>, modifier: Modifier, dismiss: () -> Unit) {
    Dialog(onDismissRequest = { dismiss() }) {
        val mod = modifier
            .background(
                shape = RoundedCornerShape(Dimens.rowCornerRadius),
                color = MaterialTheme.colorScheme.background
            )
        val padding = PaddingValues(horizontal = Dimens.hMargin, vertical = Dimens.vMargin)

        ConstraintLayout(modifier = mod) {
            val (label, button, list) = createRefs()
            val guideLineTop = createGuidelineFromTop(6.dp)
            val guideLineEnd = createGuidelineFromEnd(6.dp)

            Text(text = stringResource(id = R.string.episodes),
                modifier = Modifier.constrainAs(label) {
                start.linkTo(parent.start, Dimens.hMargin)
                top.linkTo(parent.top, 10.dp)
                end.linkTo(button.start, Dimens.hMargin)
                width = Dimension.fillToConstraints
            }, textAlign = TextAlign.Center)

            Box(
                modifier = Modifier
                    .constrainAs(button) {
                        start.linkTo(guideLineEnd)
                        top.linkTo(guideLineTop)
                        end.linkTo(guideLineEnd)
                        bottom.linkTo(guideLineTop)
                        width = Dimension.value(32.dp)
                        height = Dimension.ratio("1:1")
                    }
                    .clickable { dismiss() }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.ic_close),
                    contentDescription = "close episode list dialog",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                )
            }

            LazyColumn(
                modifier = Modifier.constrainAs(list) {
                    start.linkTo(parent.start)
                    top.linkTo(label.bottom, 10.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(Dimens.rowVSpace)
            ) {
                items(count = episodes.size) {
                    val episode = episodes[it]
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 40.dp)
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            if (episode != null) {
                                Text(text = episode.name, modifier = Modifier.fillMaxWidth())
                            } else {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.secondary,
                                    strokeWidth = 6.dp,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EpisodeListDialog_Preview() {
    val episodes = episodesMock()
    val onClick = {}
    EpisodeListDialog(episodes, Modifier.fillMaxSize(), onClick)
}