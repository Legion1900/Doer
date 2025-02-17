package com.legion1900.doer.storage

import com.legion1900.doer.common.ResourceImage

class InMemoryMockNotesStorage : NotesStorage {

    private val imageMapper = ImageMapper()

    private val currentNotes = mutableListOf<LocalNoteShort>()

    override suspend fun getShortNotes(
        offset: Int,
        limit: Int,
        isDone: Boolean
    ): List<LocalNoteShort> {
        if (offset + limit > currentNotes.size) {
            currentNotes.addAll(getMockData())
        }

        return currentNotes
            .filter { it.isDone == isDone }
            .subList(offset, offset + limit)
    }

    override suspend fun addOrUpdate(note: LocalNoteShort) {
        currentNotes.add(0, note)
    }

    override suspend fun markAsDone(noteId: String) {
        currentNotes.indexOfFirst { it.id == noteId }
            .takeIf { it != -1 }
            ?.let { currentNotes[it] = currentNotes[it].copy(isDone = true) }
    }

    private fun getMockData(size: Int = 50): Array<LocalNoteShort> {
        val offset = currentNotes.size
        val baseTitle = "Note "

        return Array(size) { index ->
            val imageIndex = index % ResourceImage.Resource.entries.size
            val resImage = ResourceImage(ResourceImage.Resource.entries[imageIndex])
            val thumbnail = imageMapper.mapToStorageImage(resImage)
            LocalNoteShort(
                (offset + index).toString(),
                title = baseTitle + (offset + index),
                dueDate = if (index % 2 == 0) 1739553691 else null,
                thumbnail = thumbnail,
                isDone = false
            )
        }
    }
}
