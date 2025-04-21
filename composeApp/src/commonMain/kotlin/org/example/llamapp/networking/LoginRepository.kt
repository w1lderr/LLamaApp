package org.example.llamapp.networking

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import multiplatform.network.cmptoast.showToast
import org.example.llamapp.model.Login

class LoginRepository {
    suspend fun LoginWithEmailAndPassword(login: Login): AuthResult? {
        val auth = Firebase.auth
        var result: AuthResult? = null

        try {
            result = auth.signInWithEmailAndPassword(login.Email, login.Password)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast("Error: $e")
            }
        }

        return result
    }
}