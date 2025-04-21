package org.example.llamapp.navigation

import com.arkivanov.decompose.ComponentContext

class SignUpScreenComponent(
    componentContext: ComponentContext,
    private val onNavigateToHomeScreen: () -> Unit,
    private val onNavigateToLoginScreen: () -> Unit
): ComponentContext by componentContext {
    fun navigateToHomeScreen() {
        onNavigateToHomeScreen()
    }

    fun navigateToLoginScreen() {
        onNavigateToLoginScreen()
    }
}