package kata

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class SocialNetworkTest {

    private val posts = mockk<Posts>(relaxed = true)

    private val console = mockk<Console>(relaxed = true)

    private val clock = Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC"))

    private val network = SocialNetwork(posts, console, clock)

    @Test
    fun `should publish messages to a personal timeline`() {
        network.submitCommand("Alice -> I love the weather today")

        verify {
            posts.add(
                userName = "Alice",
                post = Post(
                    message = "I love the weather today",
                    timestamp = LocalDateTime.parse("2007-12-03T10:15:30")
                )
            )
        }
    }

    @Test
    fun `should view user's timeline`() {
        every {
            posts.findBy("Alice")
        } returns listOf(Post("I love the weather today", LocalDateTime.parse("2007-12-03T10:20:30")))

        network.submitCommand("Alice")

        verify { console.printLine("I love the weather today (5 minutes ago)") }
    }
}
