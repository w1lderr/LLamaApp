package org.example.llamapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Messages(
    @SerialName("user_message")
    val user_message: String = "",
    @SerialName("llama_message")
    val llama_message: String = "",
)