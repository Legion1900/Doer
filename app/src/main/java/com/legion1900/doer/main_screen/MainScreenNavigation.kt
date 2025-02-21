package com.legion1900.doer.main_screen

import com.legion1900.doer.feature_list.NotesListScreenMode
import kotlinx.serialization.Serializable

sealed interface MainScreenDestination {

    val mode: NotesListScreenMode

    @Serializable
    data object ToDoScreen : MainScreenDestination {
        override val mode: NotesListScreenMode = NotesListScreenMode.TODO
    }

    @Serializable
    data object DoneScreen : MainScreenDestination {
        override val mode: NotesListScreenMode = NotesListScreenMode.DONE
    }
}
