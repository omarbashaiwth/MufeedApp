package com.omarbashawith.mufeed_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.MufeedAppTheme
import com.omarbashawith.mufeed_app.features.list.presentation.ListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MufeedAppTheme {
                Surface(
                    modifier = Modifier.background(MaterialTheme.colors.background)
                ) {
                    ListScreen()
                }

            }
        }
    }
}
