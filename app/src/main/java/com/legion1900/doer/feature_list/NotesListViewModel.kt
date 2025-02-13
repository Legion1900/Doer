package com.legion1900.doer.feature_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesListViewModel(
    private val notesListProvider: NotesListProvider
) : ViewModel() {

    private val _state = MutableStateFlow(NotesListScreenState(emptyList()))

    val state: StateFlow<NotesListScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            val notes = notesListProvider.getNotes()
            _state.value = NotesListScreenState(notes)
        }
    }
}
