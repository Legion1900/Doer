@file:OptIn(ExperimentalCoilApi::class)

package com.legion1900.doer.ui.theme.compose_ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.example.compose.DoerTheme

@Composable
fun DoerPreview(
    content: @Composable () -> Unit
) {
    val handler = AsyncImagePreviewHandler {
        FakeImage(
            color = 0x6a80a300,
        )
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides handler) {
        DoerTheme {
            content()
        }
    }
}
