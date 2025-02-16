package com.legion1900.doer.feature_list

import com.legion1900.doer.storage.ImageMapper
import com.legion1900.doer.storage.LocalNoteShort
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

class ShortNotesToCardMapper {

    private val imageMapper = ImageMapper()
    private val monthNames by lazy {
        MonthNames(
            listOf(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            )
        )
    }

    fun map(note: LocalNoteShort): NoteCardData {
        val thumbnail = imageMapper.mapLocalImage(note.thumbnail)
        val dueDate = note.dueDate?.let(::formatDueDate)
        return NoteCardData(
            note.id,
            note.title,
            thumbnail,
            dueDate,
        )
    }

    private fun formatDueDate(dueDate: Long): String {
        val localDateTime = Instant.fromEpochSeconds(dueDate)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        val format = LocalDateTime.Format {
            dayOfMonth()
            char(' ')
            monthName(monthNames)
            char(' ')
            yearTwoDigits(localDateTime.date.year)
        }

        return format.format(localDateTime)
    }
}
