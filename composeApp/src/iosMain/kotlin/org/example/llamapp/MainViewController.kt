package org.example.llamapp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.example.llamapp.navigation.RootComponent

fun MainViewController() = ComposeUIViewController {
    val root = remember {
        RootComponent(DefaultComponentContext(LifecycleRegistry()))
    }
    App(root)
}