package fyi.manpreet.brightstart

import androidx.compose.ui.window.ComposeUIViewController
import fyi.manpreet.brightstart.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    },
    content = {
        App()
    }
)