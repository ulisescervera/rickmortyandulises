package com.gmail.uli153.rickmortyandulises

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gmail.uli153.rickmortyandulises.ui.screens.MainScreen
import com.gmail.uli153.rickmortyandulises.ui.theme.RMUTheme
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RMUTheme {
//                val systemUiController = rememberSystemUiController()
//                val systemBarColor = MaterialTheme.colorScheme.background
//
//                SideEffect {
//                    systemUiController.setStatusBarColor(color = systemBarColor, darkIcons = true)
//                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}