package com.legion1900.doer.feature_list

import android.util.Log
import com.legion1900.doer.storage.NotesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class NotesListReducer(
    private val notesRepository: NotesStorage
) {

    private val mapper = ShortNotesToCardMapper()

    private val _state = MutableStateFlow(NotesListScreenState(emptyList()))
        .onStart {
            emit(
                NotesListScreenState(
                    notesRepository
                        .getShortNotes(0, 20, false)
                        .map(mapper::map)
                )
            )
            Log.d("enigma", "emitted initial state!")
        }

    val state: Flow<NotesListScreenState>
        get() = _state

    fun handleIntention(intent: NotesListIntent) {
        when (intent) {
            is NotesListIntent.MarkNoteAsDone -> {

            }

            is NotesListIntent.ScrollingDown -> {

            }
        }
    }
}
