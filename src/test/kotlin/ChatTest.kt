import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class ChatServiceTest {

    private lateinit var service: ChatService

    @Before
    fun setUp() {
        service = ChatService
    }

    @Test
    fun testAddMessage() {
        service.addMessage(0, Message("Здравствуй!", true))
        assertEquals(0, service.getUnreadChatsCount())
    }

    @Test(expected = NoChatException::class)
    fun testGetMessagesThrowsNoChatException() {
        service.getMessages(0, 1)
        assertEquals(1, service.getUnreadChatsCount())
    }

    @Test
    fun testGetLastMessage() {
        service.addMessage(0, Message("Здравствуй!", true))
        service.addMessage(1, Message("Добрый день!"))
        assertEquals("Добрый день!", service.getLastMessage())
    }
}