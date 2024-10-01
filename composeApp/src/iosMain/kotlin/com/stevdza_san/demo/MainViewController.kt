package com.stevdza_san.demo

import androidx.compose.ui.window.ComposeUIViewController
import com.stevdza_san.demo.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }