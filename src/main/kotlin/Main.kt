fun main() {
    val service = ChatService


    service.addMessage(0, Message("Здравствуй!", true))
    service.addMessage(1, Message("Добрый день!"))

    val allMessages = ChatService.getChats()
    println("Все сообщения: $allMessages")

    println("Непрочитанные сообщения: ${service.getUnreadChatsCount()}")

    try {
        service.getMessages(1, 2)
    } catch (e: NoChatException) {
        println("Сообщение отсутствует: ${e.message}")
    }

    ChatService.deleteMessage(1, 0)
    val lastMessages = ChatService.getLastMessage()
    println("Последние сообщения: $lastMessages")

    ChatService.clearChats()
    println(ChatService.getChats().size)

    ChatService.deleteChat(2)
    println(ChatService.getChats())
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

    fun getMessages(userId: Int, count: Int): List<Message> {
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

    fun deleteMessage(userId: Int, messageIndex: Int) {
        chats[userId]?.message?.removeAt(messageIndex)
    }

    fun getLastMessage(): List<String> {
        return chats.values.flatMap { chat ->
            chat.message.asSequence()
                .filter { it.text.isNotBlank() }
                .map { it.text }
        }
    }

    fun clearChats() {
        chats.clear()
    }
}


data class Message(val text: String, var read: Boolean = false)
data class Chat(val message: MutableList<Message> = mutableListOf())
