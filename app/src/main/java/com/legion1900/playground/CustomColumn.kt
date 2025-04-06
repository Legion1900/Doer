package com.legion1900.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.legion1900.doer.ui.theme.compose_ext.DoerPreview

@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Measure children
        val placeables = measurables.map { measurable -> measurable.measure(constraints) }

        // Specify the size of the layout
        layout(constraints.maxWidth, constraints.maxHeight) {
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(0, y)
                y += placeable.height
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomColumnPreview() {
    DoerPreview {
        CustomColumn(
            modifier = Modifier.background(Color.DarkGray)
        ) {
            val textCount = 5
            for (i in 0 until textCount) {
                val modifier = when {
                    i == 0 -> Modifier.padding(top = 10.dp)
                    i == (textCount - 1) -> Modifier.padding(bottom = 10.dp, top = 3.dp)
                    else -> Modifier.padding(top = 3.dp)
                }
                Text(
                    text = "Text $i",
                    modifier = modifier
                        .background(Color.Cyan)
                )
            }
        }
    }
}
