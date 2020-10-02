package ru.netology

import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun createMessage_firstMessage() {
        val ownerId = 1
        val recipientId = 2
        val text = "1 -> 2, mess1, in chat 1"

        val service = ChatService()
        val result = service.createMessage(ownerId = ownerId, recipientId = recipientId, text = text)

        assertTrue(result)
    }

    @Test
    fun createMessage_reMessage() {
        val ownerId = 1
        val recipientId = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val text2 = "2 -> 1, message 2, in chat 1"

        val service = ChatService()
        service.createMessage(ownerId = ownerId, recipientId = recipientId, text = text1)
        val result = service.createMessage(ownerId = recipientId, recipientId = ownerId, text = text2)

        assertTrue(result)
    }

    @Test
    fun removeChat_owner() {
        val ownerId = 1
        val recipientId = 2
        val text = "1 -> 2, message 1, in chat 1"
        val userId = 1
        val chatId = 1

        val service = ChatService()
        service.createMessage(ownerId = ownerId, recipientId = recipientId, text = text)
        val result = service.removeChat(userId = userId, chatId = chatId)

        assertTrue(result)
    }

    @Test
    fun removeChat_recipient() {
        val ownerId = 1
        val recipientId = 2
        val text = "1 -> 2, message 1, in chat 1"
        val userId = 2
        val chatId = 1

        val service = ChatService()
        service.createMessage(ownerId = ownerId, recipientId = recipientId, text = text)
        val result = service.removeChat(userId = userId, chatId = chatId)

        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun removeChat_ChatNotFoundException() {
        val ownerId = 1
        val recipientId = 2
        val text = "1 -> 2, message 1, in chat 1"
        val userId = 1
        val chatId = 10

        val service = ChatService()
        service.createMessage(ownerId = ownerId, recipientId = recipientId, text = text)

        service.removeChat(userId = userId, chatId = chatId)
    }

    @Test
    fun getChats() {
        val userId = 1
        val ownerId1 = 1
        val ownerId2 = 3
        val recipientId1 = 2
        val recipientId2 = 1
        val text1 = "1 -> 2, message 1, in chat 1"
        val text2 = "3 -> 1, message 2, in chat 2"

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        service.createMessage(ownerId = ownerId2, recipientId = recipientId2, text = text2)
        val result = service.getChats(userId = userId).isNotEmpty()

        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getChats_ChatNotFoundException() {
        val userId = 10
        val ownerId1 = 1
        val ownerId2 = 3
        val recipientId1 = 2
        val recipientId2 = 1
        val text1 = "1 -> 2, message 1, in chat 1"
        val text2 = "3 -> 1, message 2, in chat 2"

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        service.createMessage(ownerId = ownerId2, recipientId = recipientId2, text = text2)

        service.getChats(userId = userId)
    }

    @Test
    fun editMessage() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 1
        val messageId = 1
        val text2 = "1 -> 2, message new 1, in chat 1"

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.editMessage(userId = userId, messageId = messageId, text = text2)

        assertTrue(result)
    }

    @Test(expected = NotRightsForChangeException::class)
    fun editMessage_NotRightsForChangeException() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 10
        val messageId = 1
        val text2 = "1 -> 2, message new 1, in chat 1"

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)

        service.editMessage(userId = userId, messageId = messageId, text = text2)
    }

    @Test
    fun editMessage_notMessage() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 1
        val messageId = 10
        val text2 = "1 -> 2, message new 1, in chat 1"

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.editMessage(userId = userId, messageId = messageId, text = text2)

        assertFalse(result)
    }

    @Test
    fun getMessages() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val text2 = "1 -> 2, message 2, in chat 1"
        val text3 = "1 -> 2, message 3, in chat 1"
        val chatId = 1
        val messageId = 2
        val count = 1

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text2)
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text3)
        val result = service.getMessages(chatId = chatId, messageId = messageId, count).size == 1

        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getMessages_ChatNotFoundException() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val text2 = "1 -> 2, message 2, in chat 1"
        val text3 = "1 -> 2, message 3, in chat 1"
        val chatId = 10
        val messageId = 2
        val count = 1

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text2)
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text3)

        service.getMessages(chatId = chatId, messageId = messageId, count)
    }

    @Test
    fun removeMessage_owner() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 1
        val messageId = 1


        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.removeMessage(userId = userId, messageId = messageId)

        assertTrue(result)
    }

    @Test
    fun removeMessage_recipient() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 2
        val messageId = 1


        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.removeMessage(userId = userId, messageId = messageId)

        assertTrue(result)
    }

    @Test
    fun removeMessage_notMessage() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 1
        val messageId = 10


        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.removeMessage(userId = userId, messageId = messageId)

        assertFalse(result)
    }

    @Test
    fun getUnreadChatsCount_userOwner() {
        val ownerId1 = 1
        val ownerId2 = 3
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 1
        val text2 = "3 -> 2, message 2, in chat 1"

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        service.createMessage(ownerId = ownerId2, recipientId = recipientId1, text = text2)
        val result = service.getUnreadChatsCount(userId = userId) == 0

        assertTrue(result)
    }

    @Test
    fun getUnreadChatsCount() {
        val ownerId1 = 1
        val ownerId2 = 3
        val recipientId1 = 2
        val text1 = "1 -> 2, message 1, in chat 1"
        val userId = 2
        val text2 = "3 -> 2, message 2, in chat 1"

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        service.createMessage(ownerId = ownerId2, recipientId = recipientId1, text = text2)
        val result = service.getUnreadChatsCount(userId = userId) == 2

        assertTrue(result)
    }

    @Test
    fun readMessage_readRecipient() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, mess1, in chat 1"
        val userId = 2
        val messageId = 1

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.readMessage(userId = userId, messageId = messageId)

        assertTrue(result)
    }

    @Test
    fun readMessage_readOwner() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, mess1, in chat 1"
        val userId = 1
        val messageId = 1

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.readMessage(userId = userId, messageId = messageId)

        assertTrue(result)
    }

    @Test
    fun readMessage_notMessage() {
        val ownerId1 = 1
        val recipientId1 = 2
        val text1 = "1 -> 2, mess1, in chat 1"
        val userId = 2
        val messageId = 10

        val service = ChatService()
        service.createMessage(ownerId = ownerId1, recipientId = recipientId1, text = text1)
        val result = service.readMessage(userId = userId, messageId = messageId)

        assertFalse(result)
    }
}