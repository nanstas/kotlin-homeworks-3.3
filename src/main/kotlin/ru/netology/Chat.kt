package ru.netology

data class Chat(
    val ownerId: Int,
    val recipientId: Int,
    val chatId: Int,
    val messages: List<Message> = listOf(),
//    val isUnreadChatOwner: Boolean = false,
//    val isUnreadChatRecipient: Boolean = true,
    val isDeleteOwner: Boolean = false,
    val isDeleteRecipient: Boolean = false
)