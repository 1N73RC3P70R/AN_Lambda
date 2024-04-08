fun main() {
    val service = ChatService

    service.addMessage(0, Message("Здравствуй!", true))
    service.addMessage(1, Message("Добрый день!"))

    println("Непрочитанные сообщения: ${service.getUnreadChatsCount()}")

    try {
        service.getMessages(1, 2)
    } catch (e: NoChatException) {
        println("Сообщение отсутствует: ${e.message}")
    }

    println("Последнее: ${service.getLastMessage()}")
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
        return chats.values.count { chat ->
            chat.message.any { !it.read }
        }
    }

    fun getMessages(userId: Int, count: Int): List<Message> {
        return ChatService.chats[userId]?.message?.takeLast(count)
            ?: throw NoChatException()
    }

    fun getLastMessage(): String {
        return chats.values.mapNotNull { chat ->
            chat.message.lastOrNull { it.text.isNotBlank() }?.text
        }.lastOrNull() ?: "Сообщение не существует"
    }

    fun clearChats() {
        chats.clear()
    }
}

data class Message(val text: String, var read: Boolean = false)
data class Chat(val message: MutableList<Message> = mutableListOf())
