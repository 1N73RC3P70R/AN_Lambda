fun main() {
    val service = ChatService

    service.apply {
        addMessage(0, Message("Здравствуй!", true))
        addMessage(1, Message("Добрый день!"))
    }

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

    service.deleteMessage(1, service.getMessage(1, 1).firstOrNull())

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
        return chats.values.sumOf { chat -> chat.message.count { !it.read } }
    }

    fun getMessage(userId: Int, count: Int): List<Message> {
        val chat = chats[userId] ?: throw NoChatException()
        return chat.message.takeLast(count).filterNotNull()
    }


    fun getChats(): List<Chat> {
        return chats.values.toList()
    }

    fun deleteChat(userId: Int) {
        chats.remove(userId)
        chats[userId]?.message?.clear()
    }

    fun deleteMessage(userId: Int, message: Message?) {
        chats[userId]?.message?.remove(message)
    }

    fun getLastMessage(): List<String> {
        return chats.values.map { chat ->
            chat.message.lastOrNull()?.text ?: "НЕТ СООБЩЕНИЙ!"
        }
    }

    fun clearChats() {
        chats.clear()
    }
}

data class Message(val text: String, var read: Boolean = false)
data class Chat(val message: MutableList<Message> = mutableListOf())
