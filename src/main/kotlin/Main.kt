fun main(args: Array<String>) {
    ChatService.addMessage(1, Message("Здравствуй!"));
    ChatService.addMessage(2, Message("Добрый день!"))
    println(ChatService)
}

object ChatService {
    private val chats = mutableMapOf<Int, Chat>()

    fun addMessage(userId: Int, message: Message) {
        chats.getOrPut(userId) { Chat() }.message += message.copy();

        fun getUnreadChatsCount() = chats.values.count { it.message.any() { it.read } }

    }
}

data class Message(val text: String, val read: Boolean = false)
data class Chat(val message: MutableList<Message> = mutableListOf())
