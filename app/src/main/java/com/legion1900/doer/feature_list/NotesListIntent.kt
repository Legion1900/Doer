package com.legion1900.doer.feature_list

sealed interface NotesListIntent {

    class MarkNoteAsDone(val noteId: String) : NotesListIntent

    class ScrollingDown(
        val firstVisibleItemIndexedValue: Int,
        val isDone: Boolean,
    ) : NotesListIntent
}
