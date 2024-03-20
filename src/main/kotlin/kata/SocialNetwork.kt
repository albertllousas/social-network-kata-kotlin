package kata

import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalDateTime.now

interface Console {
    fun printLine(message: String)
}

class SocialNetwork(private val posts: Posts, private val console: Console, private val clock: Clock) {
    fun submitCommand(command: String) {
        if (command.contains(" -> ")) {
            val (userName, msg) = command.split(" -> ")
            posts.add(userName, Post(msg, now(clock)))
        } else {
            posts.findBy(command).forEach {
                console.printLine("${it.message} (${Duration.between(now(clock), it.timestamp).toMinutes()} minutes ago)")
            }
        }
    }
}
