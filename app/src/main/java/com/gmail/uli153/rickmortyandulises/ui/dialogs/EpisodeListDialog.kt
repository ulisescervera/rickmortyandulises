/**
 * Created by Ulises on 18/7/23.
 */
package com.gmail.uli153.rickmortyandulises.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gmail.uli153.rickmortyandulises.R
import com.gmail.uli153.rickmortyandulises.domain.models.EpisodeModel
import com.gmail.uli153.rickmortyandulises.ui.theme.Dimens

@Composable
fun EpisodeListDialog(episodes: List<EpisodeModel?>, dismiss: () -> Unit) {
    Dialog(onDismissRequest = { dismiss() }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(shape = RoundedCornerShape(Dimens.rowCornerRadius), color = MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(horizontal = Dimens.hMargin, vertical = Dimens.vMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.rowVSpace)
        ) {
            items(count = episodes.size) {
                val episode = episodes[it]
                ElevatedCard(modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 40.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .background(color = MaterialTheme.colorScheme.surface),
                ) {
                    if (episode != null) {
                        Text(text = episode.name, modifier = Modifier.fillMaxWidth())
                    } else {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary, strokeWidth = 6.dp, modifier = Modifier.size(32.dp))
                    }

                }
            }
        }
    }
}