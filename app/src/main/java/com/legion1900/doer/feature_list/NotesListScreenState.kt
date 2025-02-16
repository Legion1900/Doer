package com.legion1900.doer.feature_list

import androidx.compose.runtime.Immutable

@Immutable
data class NotesListScreenState(
    val notes: List<NoteCardData>
)

sealed interface NotesListScreenChanges {

    class NewPage(val notes: List<NoteCardData>) : NotesListScreenChanges
    class RemoveNote(val noteId: String) : NotesListScreenChanges
}
