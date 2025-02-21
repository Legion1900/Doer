package com.legion1900.doer.feature_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.legion1900.doer.common.ResourceImage
import com.legion1900.doer.common.vetoIf
import com.legion1900.doer.ui.theme.compose_ext.DoerPreview
import org.koin.androidx.compose.koinViewModel

enum class NotesListScreenMode(
    internal val isDone: Boolean
) {
    TODO(false),
    DONE(true)
}

@Composable
fun NotesListScreen(
    mode: NotesListScreenMode,
    viewModel: NotesListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    NotifyScrollingDown(mode, listState, viewModel)

    NotesListScreen(
        screenState,
        listState,
        onNoteDone = { noteId ->
            viewModel.handleIntention(NotesListIntent.MarkNoteAsDone(noteId))
        },
        modifier,
    )
}

@NonSkippableComposable
@Composable
private fun NotifyScrollingDown(
    mode: NotesListScreenMode,
    listState: LazyListState,
    viewModel: NotesListViewModel,
) {
    LaunchedEffect(listState) {
        // Cold flow which emits new "unique" values on every state reading.
        snapshotFlow { listState.firstVisibleItemIndex }
            .vetoIf { old, new ->
                new < old
            }
            .collect { index ->
                viewModel.handleIntention(NotesListIntent.ScrollingDown(index, mode.isDone))
            }
    }
}

@Composable
private fun NotesListScreen(
    state: NotesListScreenState,
    lazyListState: LazyListState = rememberLazyListState(),
    onNoteDone: (noteId: String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState,
        modifier = modifier.fillMaxSize(),
    ) {
        items(state.notes, key = { it.id }) { note: NoteCardData ->
            NoteCard(note, modifier = Modifier.height(250.dp)) { event ->
                when (event) {
                    is NoteCardEvent.CardClicked -> {
                        Log.e(NotesListViewModel::class.simpleName, "Not implemented!")
                    }

                    is NoteCardEvent.ResolveClicked -> onNoteDone(event.note.id)
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun NotesScreenPreview() {
    fun getMockData(size: Int = 50): List<NoteCardData> {
        val baseTitle = "Note "

        return Array(size) { index ->
            val imageIndex = index % ResourceImage.Resource.entries.size
            NoteCardData(
                index.toString(),
                title = baseTitle + index,
                dueDate = if (index % 2 == 0) "09.09.2025" else null,
                thumbnail = ResourceImage(ResourceImage.Resource.entries[imageIndex]),
                isDone = false
            )
        }.toList()
    }

    DoerPreview {

        NotesListScreen(NotesListScreenState(getMockData()))
    }
}
