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

    private fun getMockData(size: Int = 50): Array<LocalNoteShort> {
        val baseTitle = "Note "

        return Array(size) { index ->
            val imageIndex = index % ResourceImage.Resource.entries.size
            val resImage = ResourceImage(ResourceImage.Resource.entries[imageIndex])
            val thumbnail = imageMapper.mapToStorageImage(resImage)
            LocalNoteShort(
                index.toString(),
                title = baseTitle + index,
                dueDate = if (index % 2 == 0) 1739553691 else null,
                thumbnail = thumbnail,
                isDone = false
            )
        }
    }
}
