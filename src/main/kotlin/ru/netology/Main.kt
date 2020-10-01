package ru.netology

fun main() {
    val service = ChatService()

//    service.createChat(1, 2)

    service.createMessage(1, 2, "Hi, #2 in chat First")
    service.createMessage(2, 1, "Hi, #2 in chat First")
//    service.createMessage(1, 3, "Hi, #1 in chat First")
//    service.createMessage(2, 1, "How are you, #1 in chat First")
//    service.createMessage(2, 3, "Hi, #1 in chat First")
//    service.removeMessage(1,1)

    service.readMessage(2,1)
    service.readMessage(1,2)

//    service.editMessage(1, 1,"Hello, #2 in chat First")
//    service.removeMessage(1,3)
//    service.removeMessage(2,3)

    service.printChats()
    println("--------------------------------------------------------------------")
//    println(service.getChats(1))
}