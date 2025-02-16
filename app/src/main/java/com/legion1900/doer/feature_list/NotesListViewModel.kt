package com.legion1900.doer.feature_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class NotesListViewModel(
    private val reducer: NotesListReducer,
) : ViewModel() {

    val state: StateFlow<NotesListScreenState> by lazy {
        reducer
            .state
            .onEach { Log.d("enigma", "got state: ${it.notes.map { it.id }}") }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                NotesListScreenState(emptyList())
            )
    }

    fun handleIntention(intent: NotesListIntent) {
        reducer.handleIntention(intent)
    }
}
