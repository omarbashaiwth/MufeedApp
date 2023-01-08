package com.omarbashawith.mufeed_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.omarbashawith.mufeed_app.core.presentation.composables.BottomBarSection
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.MufeedAppTheme
import com.omarbashawith.mufeed_app.features.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MufeedAppTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(MaterialTheme.colors.primary)
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val hideBottomNav = navBackStackEntry?.arguments?.getBoolean(
                    ARG_BOTTOM_NAV_VISIBILITY)
                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    bottomBar = {
                        if (hideBottomNav == null || !hideBottomNav) {
                            BottomBarSection(
                                navController = navController
                            )
                        }
                    }
                ) {
                    DestinationsNavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        navGraph = NavGraphs.root
                    )
                }
            }
        }
    }
}

private const val ARG_BOTTOM_NAV_VISIBILITY = "hideBottomNav"
