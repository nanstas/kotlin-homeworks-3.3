package ru.netology

data class Message(
    val ownerId: Int,
    val recipientId: Int,
    val messageId: Int,
    val chatId: Int,
    val text: String,
    val isUnreadRecipient: Boolean = true,
    val isDeleteOwner: Boolean = false,
    val isDeleteRecipient: Boolean = false
)