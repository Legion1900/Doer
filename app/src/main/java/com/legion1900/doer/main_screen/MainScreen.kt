package com.legion1900.doer.main_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
internal fun MainScreen(
    onNoteClick: (noteId: String) -> Unit,
    onAddNoteClick: () -> Unit
) {
    Scaffold { innerPadding ->
        MainScreenNavHost(innerPadding)
    }
}

@Composable
private fun MainScreenNavHost(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    val modifier = Modifier.padding(innerPadding)
    NavHost(navController, startDestination = ToDoScreenRoute) {
        defineToDoScreen(modifier)
        defineDoneScreen(modifier)
    }
}
