package org.example.llamapp.networking

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import multiplatform.network.cmptoast.showToast
import org.example.llamapp.model.Messages
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HomeRepository(private val httpClient: HttpClient) {
    private val firestore = Firebase.firestore
    private val currentUser : FirebaseUser? = Firebase.auth.currentUser
    private val uid = currentUser!!.uid

    @OptIn(ExperimentalTime::class)
    suspend fun addMessages(messages: Messages): Unit? {
        return try {
            val timestamp = Clock.System.now().toEpochMilliseconds()
            val result = firestore.document("chats/${uid}/messages/$timestamp").set(messages)
            return result
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast("Error: $e")
            }
        }
    }

    suspend fun getMessages(): List<Messages> {
        return try {
            val result = firestore.collection("chats/${uid}/messages").get()
            result.documents.map { doc ->
                doc.data<Messages>()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast("Error: $e")
            }
            emptyList()
        }
    }

    suspend fun deleteMessages() {
        return try {
            val messages = firestore.collection("chats/${uid}/messages").get()

            messages.documents.forEach { doc ->
                firestore.document("chats/${uid}/messages/${doc.id}").delete()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast("Error: $e")
            }
        }
    }

    suspend fun TalkToLlama(text: String): String {
        return try {
            val response = httpClient.get("http://10.0.2.2:8000/question_to_llama") {
                url {
                    parameters.append("text", text)
                }
            }
            response.bodyAsText()
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}