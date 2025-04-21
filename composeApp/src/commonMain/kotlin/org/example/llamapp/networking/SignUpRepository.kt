package org.example.llamapp.networking

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import multiplatform.network.cmptoast.showToast
import org.example.llamapp.model.SignUp

class SignUpRepository {
    suspend fun SignUpWithEmailAndPassword(signUp: SignUp): AuthResult? {
        val auth = Firebase.auth
        var result: AuthResult? = null

        try {
            result = auth.createUserWithEmailAndPassword(signUp.Email, signUp.Password)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast("Error: $e")
            }
        }

        return result
    }
}