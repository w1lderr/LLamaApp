package org.example.llamapp.navigation

import com.arkivanov.decompose.ComponentContext

class DuringLoadingComponent(
    componentContext: ComponentContext,
    private val onNavigateToHomeScreen: () -> Unit,
    private val onNavigateToSignUpScreen: () -> Unit
): ComponentContext by componentContext {
    fun navigateToHomeScreen() {
        onNavigateToHomeScreen()
    }

    fun navigateToSignUpScreen() {
        onNavigateToSignUpScreen()
    }
}