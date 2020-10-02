package ru.netology

class ChatService {
    private val chats = mutableListOf<Chat>()
    private var messageId = 0
    private var chatId = 0

    private fun createChat(ownerId: Int, recipientId: Int): Chat {
        chatId += 1
        val chat = Chat(ownerId = ownerId, recipientId = recipientId, chatId = chatId)
        chats.add(chat)
        return chat
    }

    fun createMessage(ownerId: Int, recipientId: Int, text: String): Boolean {
        messageId += 1
        val chat = chats.firstOrNull { it.recipientId == recipientId && it.ownerId == ownerId || it.recipientId == ownerId && it.ownerId == recipientId }
                ?: createChat(ownerId, recipientId)
        val message = Message(ownerId = ownerId, recipientId = recipientId, chatId = chatId, text = text, messageId = messageId)
        val messages = chat.messages.toMutableList()
        messages.add(message)
        val editChat = if (chat.ownerId == message.ownerId) chat.copy(messages = messages.toList()) else
            chat.copy(messages = messages.toList())
        chats.remove(chat)
        chats.add(editChat)
        return true
    }

    private inline fun List<Chat>.findMessage(predicate: (Message) -> Boolean): Pair<Chat, Message>? {
        forEach { chat ->
            val message = chat.messages.find(predicate) ?: return@forEach
            return chat to message
        }
        return null
    }

    private fun List<Chat>.findMessageById(messageId: Int): Pair<Chat, Message>? = findMessage {
        it.messageId == messageId
    }

    fun removeChat(userId: Int, chatId: Int): Boolean {
        val chat = chats.firstOrNull { it.chatId == chatId }
                ?: throw ChatNotFoundException("no chat with id $chatId")
        val removeChat = if (chat.ownerId == userId) chat.copy(isDeleteOwner = true) else chat.copy(isDeleteRecipient = true)
        chats.remove(chat)
        chats.add(removeChat)
        return true
    }

    fun getChats(userId: Int): List<Chat> {
        val userChats = chats.filter { it.ownerId == userId && !it.isDeleteOwner || it.recipientId == userId && !it.isDeleteRecipient }
        return if (userChats.isNotEmpty()) userChats else throw ChatNotFoundException("You have not chat")
    }

    fun editMessage(userId: Int, messageId: Int, text: String): Boolean {
        val (chat, message) = chats.findMessageById(messageId) ?: return false
        val editMessage =
                if (message.ownerId == userId && !message.isDeleteOwner) message.copy(text = text, isUnreadRecipient = true) else
                    throw NotRightsForChangeException("You are not author, you have not rights for change or message is deleted")
        val messages = chat.messages.toMutableList()
        messages.remove(message)
        messages.add(editMessage)
        val editChat = chat.copy(messages = messages)
        chats.remove(chat)
        chats.add(editChat)
        return true
    }

    fun removeMessage(userId: Int, messageId: Int): Boolean {
        val (chat, message) = chats.findMessageById(messageId) ?: return false
        val editMessage =
                if (message.ownerId == userId) message.copy(isDeleteOwner = true) else
                    message.copy(isDeleteRecipient = true)
        val messages = chat.messages.toMutableList()
        messages.remove(message)
        messages.add(editMessage)
        val editChat =
                if (chat.ownerId == userId) chat.copy(messages = messages, isDeleteOwner = !messages.any { !it.isDeleteOwner }) else
                    chat.copy(messages = messages, isDeleteRecipient = !messages.any { !it.isDeleteRecipient })
        chats.remove(chat)
        chats.add(editChat)
        return true
    }

    fun getUnreadChatsCount(userId: Int): Int {
        val chatsUserId = chats.filter { it.ownerId == userId || it.recipientId == userId}
        var count = 0
        for (chat in chatsUserId) {
            for (message in chat.messages) {
                if (message.ownerId != userId && message.isUnreadRecipient) {
                    count++
                    break
                }
            }
        }
        return count
    }

    fun readMessage(userId: Int, messageId: Int): Boolean {
        val (chat, message) = chats.findMessageById(messageId) ?: return false
        val editMessage = if (message.recipientId == userId) message.copy(isUnreadRecipient = false) else return true
        val messages = chat.messages.toMutableList()
        messages.remove(message)
        messages.add(editMessage)
        val editChat = chat.copy(messages = messages.toList())
        chats.remove(chat)
        chats.add(editChat)
        return true
    }

    fun printChats() {
        for (chat in chats) {
            println(chat)
        }
    }
}