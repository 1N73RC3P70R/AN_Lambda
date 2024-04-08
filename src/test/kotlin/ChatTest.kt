import org.junit.Assert.*
import org.junit.Test

class ChatServiceTest {

    @Test
    fun testAddMessage() {
        val service = ChatService
        service.addMessage(0, Message("Здравствуй!", read = true))
        assertEquals(0, service.getUnreadChatsCount())
    }

    @Test
    fun testGetMessages() {
        val service = ChatService
        service.clearChats()
        try {
            service.getMessages(1, 1)
            fail("NoChatException не работает")
        } catch (e: NoChatException) {
            "Работает!"
        }
    }

    @Test
    fun testGetLastMessage() {
        val service = ChatService
        service.addMessage(2, Message("Последнее сообщение."))
        val lastMessages = service.getLastMessage()
        assertEquals("Последнее сообщение.", lastMessages.lastOrNull())
    }

    @Test
    fun testClearChats() {
        val service = ChatService
        service.addMessage(1, Message("Hello!"))
        service.addMessage(2, Message("Hi!"))
        service.clearChats()
        assertTrue(service.getChats().isEmpty())
    }

    @Test
    fun testDeleteMessage() {
        val service = ChatService
        service.addMessage(1, Message("Hello!"))
        service.deleteMessage(1, 0)
        assertTrue(service.getMessages(1, 1).isEmpty())
    }
}
