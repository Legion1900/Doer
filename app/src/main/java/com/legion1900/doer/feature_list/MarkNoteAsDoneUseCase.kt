package com.legion1900.doer.feature_list

import com.legion1900.doer.storage.NotesStorage

class MarkNoteAsDoneUseCase(
    private val storage: NotesStorage
) {

    suspend fun invoke(noteId: String) {
        storage.markAsDone(noteId)
    }
}
