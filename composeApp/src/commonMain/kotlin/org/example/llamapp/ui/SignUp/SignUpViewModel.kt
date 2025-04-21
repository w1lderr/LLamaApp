package org.example.llamapp.ui.SignUp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import multiplatform.network.cmptoast.showToast
import org.example.llamapp.model.SignUp
import org.example.llamapp.networking.SignUpRepository

class SignUpViewModel(private val signUpRepository: SignUpRepository) : ViewModel() {
    private val _signUp = MutableStateFlow(SignUp())
    private val _uiState = MutableStateFlow(SignUpUiState())
    val signUp: Flow<SignUp> get() = _signUp
    val uiState: Flow<SignUpUiState> get() = _uiState

    fun setEmail(value: String) {
        _signUp.value = _signUp.value.copy(Email = value)
    }

    fun setPassword(value: String) {
        _signUp.value = _signUp.value.copy(Password = value)
    }

    fun signUp() {
        val currentUser = _signUp.value

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (currentUser.Email.isNotEmpty() && currentUser.Password.isNotEmpty()) {
                    val result = signUpRepository.SignUpWithEmailAndPassword(currentUser)
                    if (result != null) {
                        withContext(Dispatchers.Main) {
                            _uiState.value = _uiState.value.copy(navigateToHomeScreen = true)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            showToast("Sign up failed")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showToast("Fill all fields")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error: $e")
                }
            }
        }
    }
}