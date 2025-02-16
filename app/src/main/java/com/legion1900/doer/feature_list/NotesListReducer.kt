package com.legion1900.doer.feature_list

class NotesListReducer {

    fun reduce(
        currentState: NotesListScreenState,
        changes: NotesListScreenChanges
    ): NotesListScreenState {
        return when (changes) {
            is NotesListScreenChanges.NewPage -> reduceNewPage(currentState, changes)
        }
    }

    private fun reduceNewPage(
        currentState: NotesListScreenState,
        changes: NotesListScreenChanges.NewPage
    ): NotesListScreenState {
        return currentState.copy(notes = currentState.notes + changes.notes)
    }
}
