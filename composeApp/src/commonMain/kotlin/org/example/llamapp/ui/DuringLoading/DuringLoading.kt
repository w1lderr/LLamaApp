package org.example.llamapp.ui.DuringLoading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import org.example.llamapp.navigation.DuringLoadingComponent

@Composable
fun DuringLoading(component: DuringLoadingComponent) {
    val viewModel = DuringLoadingViewModel()
    val uiState = viewModel.uiState.collectAsState(DuringLoadingUiState()).value

    LaunchedEffect(Unit) {
        viewModel.duringLoading()
    }

    LaunchedEffect(uiState.HomeScreen, uiState.SignUpScreen) {
        if (uiState.HomeScreen) {
            component.navigateToHomeScreen()
        } else if (uiState.SignUpScreen) {
            component.navigateToSignUpScreen()
        }
    }
}