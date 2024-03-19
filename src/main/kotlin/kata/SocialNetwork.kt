package kata

import java.time.Clock
import java.time.LocalDateTime

class SocialNetwork(private val posts: Posts, private val clock: Clock) {
    fun submitCommand(command: String) {
        val (userName, msg) = command.split(" -> ")
        posts.add(userName, Post(msg, LocalDateTime.now(clock)))
    }
}
