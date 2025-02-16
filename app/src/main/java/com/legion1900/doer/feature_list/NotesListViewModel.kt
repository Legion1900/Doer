package com.legion1900.doer.feature_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class NotesListViewModel(
    private val reducer: NotesListReducer,
    private val notesProvider: NoteCardsProvider,
) : ViewModel() {

    val state by lazy {
        val emptyState = NotesListScreenState(emptyList())
        flowOf<NotesListScreenState>()
//        MutableStateFlow(NotesListScreenState(emptyList()))
            .onStart {
                val initialState = reducer.reduce(
                    emptyState,
                    NotesListScreenChanges.NewPage(notesProvider.getNotes(0, 10, false))
                )

                emit(initialState)
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyState)
    }

    fun handleIntention(intent: NotesListIntent) {
        when (intent) {
            is NotesListIntent.MarkNoteAsDone -> TODO()
            is NotesListIntent.ScrollingDown -> TODO()
        }
    }
}
