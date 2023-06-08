package com.gmail.uli153.rickmortyandulises.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.uli153.rickmortyandulises.domain.models.CharacterStatus

@Composable
fun CharacterFilter(
    nameFilter: State<String>,
    statusFilter: State<CharacterStatus?>,
    onQueryChanged: (String) -> Unit,
    onStatusChanged: (CharacterStatus?) -> Unit,
    modifier: Modifier
) {
//    val interactionSource = remember { MutableInteractionSource() }
//    val isFocused = interactionSource.collectIsFocusedAsState()
    val shape = RoundedCornerShape(4.dp)
//    val borderModifier = if (isFocused.value) modifier.border(1.dp, MaterialTheme.colorScheme.secondary, shape) else modifier
    Box(modifier = modifier.background(Color.Red)) {
        TextField(
            label = { Text("Busca por nombre", color = Color.Red) },
            value = nameFilter.value,
            onValueChange = onQueryChanged,
//            interactionSource = interactionSource,
            placeholder = { Text("Busca por nombre") },
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape)
        )
    }

}