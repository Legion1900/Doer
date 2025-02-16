package com.legion1900.doer.feature_list

sealed interface NotesListIntent {

    class MarkNoteAsDone(val noteId: String) : NotesListIntent
    class ScrollingDown(val lastVisibleNotPosition: Int) : NotesListIntent
}
