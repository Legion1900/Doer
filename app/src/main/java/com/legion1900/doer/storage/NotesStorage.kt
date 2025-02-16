package com.legion1900.doer.storage

data class LocalNoteShort(
    val id: String,
    val title: String,
    val thumbnail: LocalNoteImage,
    val dueDate: Long?,
    val isDone: Boolean,
)

data class LocalNoteImage(
    val identifier: String,
    val source: Source
) {

    enum class Source {
        NETWORK,
        LOCAL
    }
}

interface NotesStorage {

    suspend fun getShortNotes(offset: Int, limit: Int, isDone: Boolean): List<LocalNoteShort>

    suspend fun addOrUpdate(note: LocalNoteShort)

    suspend fun markAsDone(noteId: String)
}
