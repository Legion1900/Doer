package com.legion1900.doer.feature_list

import com.legion1900.doer.storage.NotesStorage

class NoteCardsProvider(
    private val storage: NotesStorage
) {

    private val mapper = ShortNotesToCardMapper()

    suspend fun getNotes(
        offset: Int,
        limit: Int,
        isDone: Boolean
    ): List<NoteCardData> {
        return storage.getShortNotes(offset, limit, isDone)
            .map(mapper::map)
    }
}
