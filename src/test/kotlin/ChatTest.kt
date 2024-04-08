import org.junit.Assert.assertEquals
import org.junit.Assert.fail
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
        assertEquals("Последнее сообщение.", service.getLastMessage())
    }
}
