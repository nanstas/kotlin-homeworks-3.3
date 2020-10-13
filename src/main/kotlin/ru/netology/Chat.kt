package ru.netology

data class Chat(
    val ownerId: Int,
    val recipientId: Int,
    val chatId: Int,
    val messages: List<Message> = listOf(),
    val isDeleteOwner: Boolean = false,
    val isDeleteRecipient: Boolean = false
)