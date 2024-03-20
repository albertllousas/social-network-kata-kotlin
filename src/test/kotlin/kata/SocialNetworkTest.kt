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

    private val followers = mockk<Followers>(relaxed = true)

    private val console = mockk<Console>(relaxed = true)

    private val clock = Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC"))

    private val network = SocialNetwork(posts, followers, console, clock)

    @Test
    fun `should publish messages to a personal timeline`() {
        network.submitCommand("Alice -> I love the weather today")

        verify { posts.add("Alice", Post("I love the weather today", LocalDateTime.parse("2007-12-03T10:15:30"))) }
    }

    @Test
    fun `should view user's timeline`() {
        every {
            posts.findBy("Alice")
        } returns listOf(Post("I love the weather today", LocalDateTime.parse("2007-12-03T10:20:30")))

        network.submitCommand("Alice")

        verify { console.printLine("I love the weather today (5 minutes ago)") }
    }

    @Test
    fun `should view user's timeline with multiple messages`() {
        every {
            posts.findBy("Bob")
        } returns listOf(
            Post("Damn! We lost!", LocalDateTime.parse("2007-12-03T10:17:45")),
            Post("Good game though", LocalDateTime.parse("2007-12-03T10:16:35"))
        )

        network.submitCommand("Bob")

        verify {
            console.printLine("Good game though (1 minute ago)")
            console.printLine("Damn! We lost! (2 minutes ago)")
        }
    }

    @Test
    fun `should allow a user to subscribe to another user timeline`() {
        network.submitCommand("Charlie follows Alice")

        verify { followers.add(follower = "Charlie", followed = "Alice") }
    }
}
