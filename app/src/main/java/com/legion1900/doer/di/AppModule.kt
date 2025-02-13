package com.legion1900.doer.di

import com.legion1900.doer.feature_list.NotesListProvider
import com.legion1900.doer.feature_list.NotesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    singleOf(::NotesListProvider)
    viewModelOf(::NotesListViewModel)
}
