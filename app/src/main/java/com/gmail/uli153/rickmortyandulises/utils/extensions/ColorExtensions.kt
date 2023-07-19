/**
 * Created by Ulises on 19/7/23.
 */
package com.gmail.uli153.rickmortyandulises.utils.extensions

import androidx.compose.ui.graphics.Color

fun Color.disabled(): Color {
    return this.copy(alpha = 0.5f)
}