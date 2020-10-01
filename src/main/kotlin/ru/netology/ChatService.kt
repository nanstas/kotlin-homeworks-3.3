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

    fun removeChat(userId: Int, chatId: Int): Chat {
        val chat = chats.firstOrNull { it.chatId == chatId }
                ?: return throw ChatNotFoundException("no chat with id $chatId")
        val removeChat = if (chat.ownerId == userId) chat.copy(isDeleteOwner = true) else chat.copy(isDeleteRecipient = true)
        chats.remove(chat)
        chats.add(removeChat)
        return removeChat
    }

    fun getChats(userId: Int): List<Chat> {
        val userChats = chats.filter { it.ownerId == userId && !it.isDeleteOwner || it.recipientId == userId && !it.isDeleteRecipient }
        return if (userChats.isNotEmpty()) userChats else throw ChatNotFoundException("You have not chat")
    }

    fun createMessage(ownerId: Int, recipientId: Int, text: String): Int {
        messageId += 1
        val chat = chats.firstOrNull { it.recipientId == recipientId && it.ownerId == ownerId || it.recipientId == ownerId && it.ownerId == recipientId } ?: createChat(ownerId, recipientId)
        val message = Message(ownerId = ownerId, recipientId = recipientId, chatId = chatId, text = text, messageId = messageId, isReadOwner = true)
        val messages = chat.messages.toMutableList()
        messages.add(message)
        val editChat = chat.copy(messages = messages.toList())
        chats.remove(chat)
        chats.add(editChat)
        return message.messageId
    }

    fun editMessage(userId: Int, messageId: Int, text: String): Message {
        for (chat in chats) {
            for (message in chat.messages) {
                if (message.messageId == messageId) {
                    val editMessage = if (message.ownerId == userId && !message.isDeleteOwner) message.copy(text = text, isReadOwner = true, isReadRecipient = false) else throw NotRightsForChangeException("You are not author, you have not rights for change or message is deleted")
                    val messages = chat.messages.toMutableList()
                    messages.remove(message)
                    messages.add(editMessage)
                    val editChat = chat.copy(messages = messages)
                    chats.remove(chat)
                    chats.add(editChat)
                    return editMessage
                }
                throw NotRightsForChangeException("You are not author, you have not rights for change")
            }
        }
        throw MessageNotFoundException("Message with id $messageId not found")
    }

    fun removeMessage(userId: Int, messageId: Int): Boolean {
        for (chat in chats) {
            for (message in chat.messages) {
                if (message.messageId == messageId) {
                    val editMessage = if (message.ownerId == userId) message.copy(isDeleteOwner = true) else message.copy(isDeleteRecipient = true)
                    val messages = chat.messages.toMutableList()
                    messages.remove(message)
                    messages.add(editMessage)
                    val editChat = chat.copy(messages = messages)
                    chats.remove(chat)
                    chats.add(editChat)
                    return true
                }
            }
        }
        return false
    }

    fun getUnreadChatsCount(userId: Int): Int {

    }

    fun readMessage(userId: Int, messageId: Int): Message {
        for (chat in chats) {
            for (message in chat.messages) {
                if (message.messageId == messageId) {
                    val readMessage = if (message.ownerId == userId) message.copy(isReadOwner = true) else message.copy(isReadRecipient = true)
                    val messages = chat.messages.toMutableList()
                    messages.remove(message)
                    messages.add(readMessage)
                    val editChat = chat.copy(messages = messages)
                    chats.remove(chat)
                    chats.add(editChat)
                    return readMessage
                }
            }
        }
        throw MessageNotFoundException("Message with id $messageId not found")
    }


    fun printChats() {
        for (chat in chats) {
            println(chat)
        }
    }
}