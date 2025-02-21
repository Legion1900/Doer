package com.legion1900.doer.main_screen

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.legion1900.doer.feature_list.NotesListScreen
import com.legion1900.doer.feature_list.NotesListScreenMode
import kotlinx.serialization.Serializable

@Serializable
object ToDoScreenRoute

@Serializable
object DoneScreenRoute

fun NavGraphBuilder.defineToDoScreen(
    modifier: Modifier,
) {
    composable<ToDoScreenRoute> {
        NotesListScreen(mode = NotesListScreenMode.TODO, modifier = modifier)
    }
}

fun NavGraphBuilder.defineDoneScreen(
    modifier: Modifier
) {
    composable<DoneScreenRoute> {
        NotesListScreen(mode = NotesListScreenMode.DONE, modifier = modifier)
    }
}
