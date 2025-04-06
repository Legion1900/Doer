package com.legion1900.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.legion1900.doer.ui.theme.compose_ext.DoerPreview
import kotlin.math.max

private val DIVIDER_PADDING = 6.dp

@Composable
private fun CalendarDay(
    hoursCount: Int,
    hourHeight: Dp,
    modifier: Modifier,
    hourLabel: @Composable (hour: Int) -> Unit,
    hourDivider: @Composable (hour: Int) -> Unit,
) {
    val hourLabels = @Composable { repeat(hoursCount) { hourLabel(it) } }
    val hourDividers = @Composable { repeat(hoursCount, ) { hourDivider(it) } }
    Layout(
        contents = listOf(hourLabels, hourDividers),
        modifier = modifier
    ) { (hourLabelMeasurables, hourDividerMeasurables), constraints ->
        val hourLabelPlaceables = measureAsIs(constraints, hourLabelMeasurables)
        // TODO: fix size measuring, we must adhere to max constraints
        val hourDividerPlaceables = measureAsIs(constraints, hourDividerMeasurables)

        layout(hoursCount, hourHeight, constraints, hourLabelPlaceables, hourDividerPlaceables)
    }
}

private fun measureAsIs(
    constraints: Constraints,
    hourLabelMeasurables: List<Measurable>
): List<Placeable> {
    return hourLabelMeasurables.map { it.measure(constraints) }
}

private fun MeasureScope.layout(
    hoursCount: Int,
    hourHeight: Dp,
    constraints: Constraints,
    hourLabelPlaceables: List<Placeable>,
    dividerPlaceables: List<Placeable>
): MeasureResult {
    val height = hourHeight.roundToPx() * hoursCount
    val dividerOffset = hourLabelPlaceables.maxOf { it.width } + DIVIDER_PADDING.roundToPx()
    return layout(width = constraints.maxWidth, height = height) {
        for (i in hourLabelPlaceables.indices) {
            val labelPlaceable = hourLabelPlaceables[i]
            val dividerPlaceable = dividerPlaceables[i]
            val labelYPosition = max(i * hourHeight.roundToPx() - labelPlaceable.height, 0)
            val dividerYPosition = labelYPosition + labelPlaceable.height - dividerPlaceable.height

            labelPlaceable.placeRelative(x = 0, y = labelYPosition)
            dividerPlaceable.placeRelative(x = dividerOffset, y = dividerYPosition)
        }
    }
}

@LayoutScopeMarker
@Immutable
object CalendarDayScope {

}


@Preview(showBackground = true)
@Composable
private fun CalendarDayPreview() {
    val hoursCount = 24

    @Composable
    fun HourLabel(hour: Int) {
        if (hour == 0 || hour == hoursCount) {
            Spacer(Modifier)
        } else {
            Text(text = if (hour < 10) "0$hour" else hour.toString())
        }
    }

    @Composable
    fun Divider(hour: Int) {
        if (hour == 0 || hour == hoursCount) {
            Spacer(Modifier)
        } else {
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.Gray
            )
        }
    }

    DoerPreview {
        CalendarDay(
            hoursCount = 24,
            hourHeight = 50.dp,
            hourLabel = { hour -> HourLabel(hour) },
            hourDivider = { hour -> Divider(hour) },
            modifier = Modifier.verticalScroll(rememberScrollState())
                .padding(start = 10.dp),
        )
    }
}
