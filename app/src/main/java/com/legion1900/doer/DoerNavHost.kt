package com.legion1900.doer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.legion1900.doer.main_screen.MainScreenRoute
import com.legion1900.doer.main_screen.defineMainScreen

@Composable
fun DoerNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreenRoute) {
        defineMainScreen(
            onNoteClick = { },
            onAddNoteClick = { }
        )
    }
}
