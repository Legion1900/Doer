package com.legion1900.doer.feature_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 10

class NotesListViewModel(
    private val reducer: NotesListReducer,
    private val notesProvider: NoteCardsProvider,
    private val markNoteAsDoneUseCase: MarkNoteAsDoneUseCase
) : ViewModel() {

    private val _state = MutableSharedFlow<NotesListScreenState>()

    val state by lazy {
        val emptyState = NotesListScreenState(emptyList())
        _state
            .onStart {
                val data = notesProvider.getNotes(0, PAGE_SIZE, false)
                val initialState = reducer.reduce(
                    emptyState,
                    NotesListScreenChanges.NewPage(data)
                )

                emit(initialState)
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, NotesListScreenState(emptyList()))
    }

    fun handleIntention(intent: NotesListIntent) {
        when (intent) {
            is NotesListIntent.MarkNoteAsDone -> handleMarkNoteAsDone(intent)
            is NotesListIntent.ScrollingDown -> {
                handleScrollingDown(intent)
            }
        }
    }

    private fun handleMarkNoteAsDone(intent: NotesListIntent.MarkNoteAsDone) {
        viewModelScope.launch {
            markNoteAsDoneUseCase.invoke(intent.noteId)
        }
        viewModelScope.launch {
            val newState =
                reducer.reduce(state.value, NotesListScreenChanges.RemoveNote(intent.noteId))
            _state.emit(newState)
        }
    }

    private fun handleScrollingDown(intent: NotesListIntent.ScrollingDown) {
        val pagePart = intent.firstVisibleItemIndexedValue % PAGE_SIZE
        if (pagePart >= PAGE_SIZE / 2) {
            loadNextPage()
        }
    }

    private fun loadNextPage() {
        viewModelScope.launch {
            val offset = state.value.notes.size
            val data = notesProvider.getNotes(offset, PAGE_SIZE, false)
            val newState = reducer.reduce(state.value, NotesListScreenChanges.NewPage(data))
            _state.emit(newState)
        }
    }
}
