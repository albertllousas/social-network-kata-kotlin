package kata

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime.parse
import java.time.ZoneId

class SocialNetworkTest {

    private val posts = mockk<Posts>(relaxed = true)

    private val followers = mockk<Followers>(relaxed = true)

    private val printLine = mockk<(String) -> Unit>(relaxed = true)

    private val clock = Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC"))

    private val network = SocialNetwork(posts, followers, printLine, clock)

    @Test
    fun `should publish messages to a personal timeline`() {
        network.submitCommand("Alice -> I love the weather today")

        verify { posts.add(Post("Alice", "I love the weather today", parse("2007-12-03T10:15:30"))) }
    }

    @Test
    fun `should view user's timeline`() {
        every {
            posts.findBy("Alice")
        } returns listOf(Post("Alice", "I love the weather today", parse("2007-12-03T10:10:05")))

        network.submitCommand("Alice")

        verify { printLine("I love the weather today (5 minutes ago)") }
    }

    @Test
    fun `should view user's timeline with multiple messages`() {
        every {
            posts.findBy("Bob")
        } returns listOf(
            Post("Bob", "Damn! We lost!", parse("2007-12-03T10:13:10")),
            Post("Bob", "Good game though", parse("2007-12-03T10:14:01"))
        )

        network.submitCommand("Bob")

        verify {
            printLine("Good game though (1 minute ago)")
            printLine("Damn! We lost! (2 minutes ago)")
        }
    }

    @Test
    fun `should allow a user to subscribe to another user timeline`() {
        network.submitCommand("Charlie follows Alice")

        verify { followers.add(follower = "Charlie", followed = "Alice") }
    }

    @Test
    fun `should view an aggregated list of all subscriptions`() {
        every {
           followers.findFollowedBy("Charlie")
        } returns listOf("Alice", "Bob")
        every {
            posts.findBy("Alice")
        } returns listOf(Post("Alice", "I love the weather today", parse("2007-12-03T10:10:30")))
        every {
            posts.findBy("Bob")
        } returns listOf(
            Post("Bob", "Damn! We lost!", parse("2007-12-03T10:13:00")),
            Post("Bob", "Good game though", parse("2007-12-03T10:14:05"))
        )
        every {
            posts.findBy("Charlie")
        } returns listOf(Post("Charlie", "I'm in New York today! Anyone wants to have a coffee?", parse("2007-12-03T10:15:15")))

        network.submitCommand("Charlie wall")

        verify {
            printLine("Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)")
            printLine("Bob - Good game though (1 minute ago)")
            printLine("Bob - Damn! We lost! (2 minutes ago)")
            printLine("Alice - I love the weather today (5 minutes ago)")
        }
    }
}
