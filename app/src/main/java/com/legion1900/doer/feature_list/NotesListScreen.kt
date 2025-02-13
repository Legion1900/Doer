package com.legion1900.doer.feature_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.legion1900.doer.image_providers.DefaultImageProvider
import com.legion1900.doer.ui.theme.compose_ext.DoerPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.state.collectAsStateWithLifecycle()
    NotesListScreen(screenState, modifier)
}

@Composable
private fun NotesListScreen(
    state: NotesListScreenState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(state.notes.size, key = { state.notes[it].id }) { index ->
            NoteCard(state.notes[index], modifier = Modifier.height(250.dp))
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun NotesScreenPreview() {
    fun getMockData(size: Int = 50): List<NoteCardData> {
        val baseTitle = "Note "

        return Array(size) { index ->
            val imageIndex = index % DefaultImageProvider.stockImageRes.size
            NoteCardData(
                index.toString(),
                title = baseTitle + index,
                dueDate = if (index % 2 == 0) "09.09.2025" else null,
                thumbnail = DefaultImageProvider.stockImageRes[imageIndex]
            )
        }.toList()
    }

    DoerPreview {

        NotesListScreen(NotesListScreenState(getMockData()))
    }
}
