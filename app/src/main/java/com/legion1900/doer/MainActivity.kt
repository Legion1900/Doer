package com.legion1900.doer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.util.DebugLogger
import com.legion1900.doer.di.appModule
import com.legion1900.doer.main_screen.MainScreen
import com.legion1900.doer.ui.theme.DoerTheme
import org.koin.compose.KoinApplication
import org.koin.core.context.stopKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            initCoilImageLoader()
            App()
        }
    }

    @Composable
    private fun App() {
        KoinApplication(
            {
                modules(appModule)
            }
        ) {
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

    override fun onDestroy() {
        // Mandatory call to make sure koin app will not be recreated again on next onCreate call.
        // Otherwise it will crash app.
        stopKoin()
        super.onDestroy()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoerTheme {
        Greeting("Android")
    }
}