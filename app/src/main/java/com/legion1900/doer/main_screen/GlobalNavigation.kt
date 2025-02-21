package com.legion1900.doer.main_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object MainScreenRoute

fun NavGraphBuilder.defineMainScreen(
    onNoteClick: (noteId: String) -> Unit,
    onAddNoteClick: () -> Unit
) {
    composable<MainScreenRoute> {
        MainScreen(onNoteClick = onNoteClick, onAddNoteClick = onAddNoteClick)
    }
}

fun NavController.openMainScreen() {
    navigate(MainScreenRoute)
}
