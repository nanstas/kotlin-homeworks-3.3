package ru.netology

data class Message(
    val ownerId: Int,
    val recipientId: Int,
    val messageId: Int,
    val chatId: Int,
    val text: String,
    val isReadOwner: Boolean = false,
    val isReadRecipient: Boolean = false,
    val isDeleteOwner: Boolean = false,
    val isDeleteRecipient: Boolean = false
)