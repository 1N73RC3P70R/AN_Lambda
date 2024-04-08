fun main() {
    val service = ChatService

    service.addMessage(0, Message("Здравствуй!", true))
    service.addMessage(1, Message("Добрый день!"))


    val allMessages = ChatService.getChats()
    println("Все сообщения: $allMessages")

    println("Непрочитанные сообщения: ${service.getUnreadChatsCount()}")

    try {
        service.getMessage(1, 2)
    } catch (e: NoChatException) {
        println("Сообщение отсутствует: ${e.message}")
    }

    var lastMessages = service.getLastMessage()
    println("Последние сообщения: $lastMessages")

    service.deleteMessage(1, service.getMessage(1, 1)[0])

    lastMessages = service.getLastMessage()
    println("Последние сообщения после удаления: $lastMessages")

    service.clearChats()
    println(service.getChats().size)

    service.deleteChat(2)
    println(service.getChats())
}


class NoChatException : Exception()

object ChatService {
    private val chats = mutableMapOf<Int, Chat>()

    fun addMessage(userId: Int, message: Message) {
        val chat = chats.getOrPut(userId) { Chat() }
        chat.message.add(message)
        message.read = true
    }

    fun getUnreadChatsCount(): Int {
        return chats.values.count { chat -> chat.message.any { !it.read } }
    }

    fun getMessage(userId: Int, count: Int): List<Message> {
        val chat = chats[userId] ?: throw NoChatException()
        return chat.message.takeLast(count)
    }

    fun getChats(): List<Chat> {
        return chats.values.toList()
    }

    fun deleteChat(userId: Int) {
        chats.remove(userId)
        chats[userId]?.message?.clear()
    }

    fun deleteMessage(userId: Int, message: Message) {
        chats[userId]?.message?.remove(message)
    }


    fun getLastMessage(): List<String> {
        return chats.values.flatMap { chat ->
            val lastMessages = chat.message.lastOrNull()?.text
            if (lastMessages != null && lastMessages.isNotBlank()) {
                listOf(lastMessages)
            } else {
                emptyList()
            }
        }
    }

    fun clearChats() {
        chats.clear()
    }
}

data class Message(val text: String, var read: Boolean = false)
data class Chat(val message: MutableList<Message> = mutableListOf())
