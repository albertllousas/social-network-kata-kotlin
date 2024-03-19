package kata

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class SocialNetworkTest {

    @Test
    fun `should publish messages to a personal timeline`() {
        val posts = mockk<Posts>(relaxed = true)
        val network = SocialNetwork(posts)

        network.submitCommand("Alice -> I love the weather today")

        verify { posts.add("Alice", "I love the weather today") }
    }
}
