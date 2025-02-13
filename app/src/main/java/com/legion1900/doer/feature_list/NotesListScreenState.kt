package com.legion1900.doer.feature_list

import androidx.compose.runtime.Immutable

@Immutable
data class NotesListScreenState(
    val notes: List<NoteCardData>
)
