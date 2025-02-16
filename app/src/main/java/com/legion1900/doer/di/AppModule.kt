package com.legion1900.doer.di

import com.legion1900.doer.feature_list.MarkNoteAsDoneUseCase
import com.legion1900.doer.feature_list.NoteCardsProvider
import com.legion1900.doer.feature_list.NotesListReducer
import com.legion1900.doer.feature_list.NotesListViewModel
import com.legion1900.doer.storage.InMemoryMockNotesStorage
import com.legion1900.doer.storage.NotesStorage
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    singleOf(::InMemoryMockNotesStorage) bind NotesStorage::class
    factoryOf(::NoteCardsProvider)
    factoryOf(::NotesListReducer)
    factoryOf(::MarkNoteAsDoneUseCase)
    viewModelOf(::NotesListViewModel)
}
