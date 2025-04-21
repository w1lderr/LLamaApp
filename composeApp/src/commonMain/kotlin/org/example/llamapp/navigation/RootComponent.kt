package org.example.llamapp.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.DuringLoading,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when(config) {
            Configuration.DuringLoading -> Child.DuringLoading(
                DuringLoadingComponent(
                    componentContext = context,
                    onNavigateToHomeScreen =  { navigation.pushNew(Configuration.HomeScreen) },
                    onNavigateToSignUpScreen = { navigation.pushNew(Configuration.SignUpScreen) }
                )
            )
            Configuration.HomeScreen -> Child.HomeScreen(
                HomeScreenComponent(context)
            )
            Configuration.LoginScreen -> Child.LoginScreen(
                LoginScreenComponent(
                    componentContext = context,
                    onNavigateToHomeScreen = { navigation.pushNew(Configuration.HomeScreen) },
                    onNavigateToSignUpScreen = { navigation.pop() },
                )
            )
            Configuration.SignUpScreen -> Child.SignUpScreen(
                SignUpScreenComponent(
                    componentContext = context,
                    onNavigateToHomeScreen = { navigation.pushNew(Configuration.HomeScreen) },
                    onNavigateToLoginScreen = { navigation.pushNew(Configuration.LoginScreen) }
                )
            )
        }
    }

    sealed class Child {
        data class DuringLoading(val component: DuringLoadingComponent): Child()
        data class HomeScreen(val component: HomeScreenComponent): Child()
        data class LoginScreen(val component: LoginScreenComponent): Child()
        data class SignUpScreen(val component: SignUpScreenComponent): Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object DuringLoading: Configuration()
        @Serializable
        data object HomeScreen: Configuration()
        @Serializable
        data object LoginScreen: Configuration()
        @Serializable
        data object SignUpScreen: Configuration()
    }
}