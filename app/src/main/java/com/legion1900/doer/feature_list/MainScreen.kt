package com.legion1900.doer.feature_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {
    Scaffold { innerPadding ->
        NotesListScreen(modifier = Modifier.padding(innerPadding))
    }
}
