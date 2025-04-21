package org.example.llamapp.ui.DuringLoading

import androidx.lifecycle.ViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DuringLoadingViewModel: ViewModel() {
    private val _currentUser = Firebase.auth.currentUser
    private val _uiState = MutableStateFlow(DuringLoadingUiState())
    val uiState: Flow<DuringLoadingUiState> get() = _uiState
    fun setHomeScreenValue(value: Boolean) {
        _uiState.value = _uiState.value.copy(HomeScreen = value)
    }

    fun setSignUpScreenValue(value: Boolean) {
        _uiState.value = _uiState.value.copy(SignUpScreen = value)
    }
    fun duringLoading() {
        if (_currentUser != null) {
            setHomeScreenValue(true)
        } else {
            setSignUpScreenValue(true)
        }
    }
}