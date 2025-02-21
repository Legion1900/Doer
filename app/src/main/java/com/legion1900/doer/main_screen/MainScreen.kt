package com.legion1900.doer.main_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.legion1900.doer.feature_list.NotesListScreen
import com.legion1900.doer.feature_list.NotesListScreenMode

@Composable
fun MainScreen(
    onNoteClick: (noteId: String) -> Unit,
    onAddNoteClick: () -> Unit
) {
    Scaffold { innerPadding ->
        NotesListScreen(mode = NotesListScreenMode.TODO, modifier = Modifier.padding(innerPadding))
    }
}
