package com.legion1900.doer.feature_list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.legion1900.doer.R
import com.legion1900.doer.common.DoerImage
import com.legion1900.doer.common.coilModel
import com.legion1900.doer.ui.theme.compose_ext.DoerPreview

data class NoteCardData(
    val id: String,
    val title: String,
    val thumbnail: DoerImage,
    val dueDate: String?,
)

sealed interface NoteCardEvent {

    val note: NoteCardData

    class CardClicked(override val note: NoteCardData) : NoteCardEvent
    class ResolveClicked(override val note: NoteCardData) : NoteCardEvent
}

@Composable
fun NoteCard(
    noteCardData: NoteCardData,
    cornerRadius: Dp = dimensionResource(R.dimen.note_card_radius),
    modifier: Modifier = Modifier,
    onClick: ((NoteCardEvent) -> Unit)? = null,
) {
    val cardShape = remember { RoundedCornerShape(cornerRadius) }
    val titleHorizontalPadding = 6.dp
    val textVerticalPadding = 12.dp

    Card(
        shape = cardShape,
        modifier = modifier
            .clickable { onClick?.invoke(NoteCardEvent.CardClicked(noteCardData)) }
    ) {
        val thumbnailDescription =
            stringResource(R.string.note_card_thumbnail_accessibility, noteCardData.title)

        Column {
            AsyncImage(
                model = noteCardData.thumbnail.coilModel,
                contentScale = ContentScale.Crop,
                contentDescription = thumbnailDescription,
                modifier = Modifier
                    .weight(1f) // Take all unused space by UNWIEGHTED elements
                    .clip(cardShape)
            )

            Spacer(modifier = Modifier.height(textVerticalPadding))

            Row {
                Column {
                    Text(
                        text = noteCardData.title,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(
                            start = titleHorizontalPadding,
                            end = titleHorizontalPadding
                        )
                    )

                    if (noteCardData.dueDate != null) {
                        Text(
                            text = stringResource(
                                R.string.note_card_due_date,
                                noteCardData.dueDate
                            ),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.padding(
                                top = 4.dp,
                                start = titleHorizontalPadding,
                                end = titleHorizontalPadding,
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = { onClick?.invoke(NoteCardEvent.ResolveClicked(noteCardData)) },

                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = stringResource(R.string.note_card_resolve_button_accessibility),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(Modifier.width(titleHorizontalPadding))
            }

            Spacer(modifier = Modifier.height(textVerticalPadding))
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", name = "NoteCard with due date")
@Composable
private fun NoteCardWithDueDatePreview() {
    DoerPreview {
        NoteCard(
            noteCardData = NoteCardData(
                id = "1",
                title = "Cool note",
                thumbnail = DoerImage.ResourceImage(R.drawable.baloon),
                dueDate = "12/12/2021"
            ),
            modifier = Modifier.height(300.dp)
        ) { event ->
            Log.d("NoteCardWithDueDatePreview", "Note ${event.note.title} got event $event")
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", name = "NoteCard no due date")
@Composable
private fun NoteCardNoDueDatePreview() {
    DoerPreview {
        NoteCard(
            noteCardData = NoteCardData(
                id = "1",
                title = "Cool note",
                thumbnail = DoerImage.ResourceImage(R.drawable.coast),
                dueDate = null
            )
        )
    }
}
