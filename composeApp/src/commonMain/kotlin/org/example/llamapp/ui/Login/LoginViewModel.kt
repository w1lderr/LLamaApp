package org.example.llamapp.ui.Login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import multiplatform.network.cmptoast.showToast
import org.example.llamapp.model.Login
import org.example.llamapp.networking.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {
    private val _login = MutableStateFlow(Login())
    private val _uiState = MutableStateFlow(LoginUiState())
    val login: Flow<Login> get() = _login
    val uiState: Flow<LoginUiState> get() = _uiState

    fun setEmail(value: String) {
        _login.value = _login.value.copy(Email = value)
    }

    fun setPassword(value: String) {
        _login.value = _login.value.copy(Password = value)
    }

    fun login() {
        val currentUser = _login.value

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (currentUser.Email.isNotEmpty() && currentUser.Password.isNotEmpty()) {
                    val result = loginRepository.LoginWithEmailAndPassword(currentUser)

                    if(result != null) {
                        withContext(Dispatchers.Main) {
                            _uiState.value = _uiState.value.copy(navigateToHomeScreen = true)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            showToast(result.toString())
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