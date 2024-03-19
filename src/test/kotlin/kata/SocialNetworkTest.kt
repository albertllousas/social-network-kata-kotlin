package kata

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class SocialNetworkTest {

    @Test
    fun `should publish messages to a personal timeline`() {
        val posts = mockk<Posts>(relaxed = true)
        val clock = Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC"))
        val network = SocialNetwork(posts, clock)

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

//    @Test
//    fun `should view user's timeline`() {
//        val posts = mockk<Posts>(relaxed = true)
//        val network = SocialNetwork(posts)
//        every { posts.findBy("Alice") } returns listOf("I love the weather today")
//
//        network.submitCommand("Alice")
//
//        verify { console.println("I love the weather today (5 minutes ago)") }
//    }
}
