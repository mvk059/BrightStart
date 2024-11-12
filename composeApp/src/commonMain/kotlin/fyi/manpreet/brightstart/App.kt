package fyi.manpreet.brightstart

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import fyi.manpreet.brightstart.ui.theme.BrightStartTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        BrightStartTheme {
            Landing()
        }
    }
}