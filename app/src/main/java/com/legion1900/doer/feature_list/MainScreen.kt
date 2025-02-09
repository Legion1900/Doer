package com.legion1900.doer.feature_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.legion1900.doer.ui.theme.compose_ext.DoerPreview

@Composable
fun MainScreen() {
    Scaffold { innerPadding ->
        NotesListScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun NotesListScreen(modifier: Modifier = Modifier) {
    // TODO: change it with a proper data source.
    val data = remember { getMockData() }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(data.size, key = { data[it].id }) { index ->
            NoteCard(data[index], modifier = Modifier.height(250.dp))
        }
    }
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

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun NotesScreenPreview() {
    DoerPreview {
        NotesListScreen()
    }
}
