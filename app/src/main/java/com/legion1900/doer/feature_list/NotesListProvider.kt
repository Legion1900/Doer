package com.legion1900.doer.feature_list

import com.legion1900.doer.image_providers.DefaultImageProvider

class NotesListProvider {

    private val currentNotes by lazy {
        mutableListOf(*getMockData())
    }

    suspend fun getNotes(): List<NoteCardData> {
        return currentNotes
    }

    private fun getMockData(size: Int = 50): Array<NoteCardData> {
        val baseTitle = "Note "

        return Array(size) { index ->
            val imageIndex = index % DefaultImageProvider.stockImageRes.size
            NoteCardData(
                index.toString(),
                title = baseTitle + index,
                dueDate = if (index % 2 == 0) "09.09.2025" else null,
                thumbnail = DefaultImageProvider.stockImageRes[imageIndex]
            )
        }
    }
}
