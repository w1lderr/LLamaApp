package org.example.llamapp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.llamapp.di.appModule
import org.example.llamapp.navigation.RootComponent
import org.example.llamapp.ui.DuringLoading.DuringLoading
import org.example.llamapp.ui.Home.HomeScreen
import org.example.llamapp.ui.Login.LoginScreen
import org.example.llamapp.ui.SignUp.SignUpScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    val childStack by rootComponent.childStack.subscribeAsState()
    KoinApplication(application = {
        modules(appModule())
    }) {
        MaterialTheme {
            Children(
                stack = childStack,
                animation = stackAnimation(slide())
            ) { child ->
                when(val instance = child.instance) {
                    is RootComponent.Child.DuringLoading -> DuringLoading(component = instance.component)
                    is RootComponent.Child.HomeScreen -> HomeScreen()
                    is RootComponent.Child.LoginScreen -> LoginScreen(component = instance.component)
                    is RootComponent.Child.SignUpScreen -> SignUpScreen(component = instance.component)
                }
            }
        }
    }
}