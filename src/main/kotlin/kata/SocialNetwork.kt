package kata

import java.time.Clock
import java.time.Duration
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
            posts.findBy(command).sortedByDescending { it.timestamp }.forEach { console.printLine(it.format()) }
        }
    }

    private fun Post.format(): String {
        val minutesAgo = Duration.between(now(clock), timestamp).toMinutes()
        return if(minutesAgo <= 1L) return "$message (1 minute ago)"
        else "$message ($minutesAgo minutes ago)"
    }
}
