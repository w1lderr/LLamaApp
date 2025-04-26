package org.example.llamapp.ui.Home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import multiplatform.network.cmptoast.showToast
import org.example.llamapp.model.Messages
import org.example.llamapp.networking.HomeRepository

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private val _message = MutableStateFlow("")
    private val _listOfMessages = MutableStateFlow<List<String>>(emptyList())
    val message: Flow<String> get() = _message
    val listOfMessages: Flow<List<String>> get() = _listOfMessages

    fun setMessage(message: String) {
        _message.value = message
    }

    fun addMessageToTheList(message: String) {
        _listOfMessages.value += message
    }

    fun deleteMessages() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                homeRepository.deleteMessages()
                _listOfMessages.value = emptyList()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error: ${e.message}")
                }
            }
        }
    }

    private fun getMessages() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val messages = homeRepository.getMessages()
                val messagesList = messages.flatMap { listOf(it.user_message, it.llama_message) }
                _listOfMessages.value = messagesList
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error: ${e.message}")
                }
            }
        }
    }

    init {
        getMessages()
    }

    fun TalkToLlama() {
        val currentMessage = _message.value

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = homeRepository.TalkToLlama(currentMessage).trim().removeSurrounding("\"")
                addMessageToTheList(cleanResponse(response))
                homeRepository.addMessages(
                    Messages(
                        user_message = currentMessage,
                        llama_message = response
                    )
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error during network request: ${e.message}")
                }
            }
        }
    }

    private fun cleanResponse(response: String): String {
        return response
            .replace("\\n", "\n") // Convert \n to actual newlines
            .replace("\\*\\*(.+?)\\*\\*".toRegex(), "$1") // Remove bold markdown
            .replace("\\*\\s".toRegex(), "• ") // Convert * list items to bullets
            .trim()
    }
}