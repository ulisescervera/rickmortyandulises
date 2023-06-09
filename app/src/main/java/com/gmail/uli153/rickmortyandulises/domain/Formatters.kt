package com.gmail.uli153.rickmortyandulises.domain

import java.text.SimpleDateFormat
import java.util.Locale

object Formatters {
    val remoteDateFormatter get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
}