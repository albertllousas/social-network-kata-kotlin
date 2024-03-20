package kata

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime.parse
import java.time.ZoneId

class PostFormatterTest {

    private val clock = Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC"))

    @TestFactory
    fun `formatting posts`() = listOf(
        Triple(Post("Alice", "Hello!", parse("2007-12-03T10:15:32")), false, "Hello! (2 seconds ago)"),
        Triple(Post("Alice", "Hello!", parse("2007-12-03T10:15:32")), true, "Alice - Hello! (2 seconds ago)"),
        Triple(Post("Bob", "Damn!", parse("2007-12-03T10:17:45")), true, "Bob - Damn! (2 minutes ago)")
    ).map { (post, showUser, expected) ->
        dynamicTest("should format a post") {
            PostFormatter.format(post, clock, showUser) shouldBe expected
        }
    }
}
