package com.legion1900.doer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.util.DebugLogger
import com.legion1900.doer.main_screen.MainScreen
import com.legion1900.doer.ui.theme.DoerTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            initCoilImageLoader()
            App()
        }
    }

    @OptIn(KoinExperimentalAPI::class)
    @Composable
    private fun App() {
        KoinAndroidContext {
            DoerTheme {
                MainScreen()
            }
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    private fun initCoilImageLoader() {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .logger(DebugLogger())
                .build()
        }
    }
}
