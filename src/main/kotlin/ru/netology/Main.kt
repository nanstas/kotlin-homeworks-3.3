package ru.netology

fun main() {
    val service = ChatService()

    service.createMessage(1, 2, "1 -> 2 mess 1, in chat 1")
//    service.readMessage(2, 1)
//    service.createMessage(1, 2, "2 -> 2 mess2, in chat 1")
//    service.readMessage(2, 2)
//    service.removeChat(2,1)
//    service.readMessage(2,1)
    service.removeMessage(2,1)



    service.printChats()
    println(service.getUnreadChatsCount(2))
}