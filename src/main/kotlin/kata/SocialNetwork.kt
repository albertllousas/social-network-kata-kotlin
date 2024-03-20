package kata

import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime.now

class SocialNetwork(
    private val posts: Posts,
    private val followers: Followers,
    private val printLine: (String) -> Unit ,
    private val clock: Clock
) {

    fun submitCommand(command: String) = when {
        command.contains(" -> ") -> command.split(" -> ").let { addPost(it[0], it[1]) }
        command.contains(" follows ") -> command.split(" follows ").let { addFollower(it[0], it[1]) }
        else -> viewUserTimeline(command)
    }

    private fun addPost(userName: String, message: String) = posts.add(userName, Post(message, now(clock)))

    private fun addFollower(follower: String, followed: String) = followers.add(follower, followed)

    private fun viewUserTimeline(userName: String) =
        posts.findBy(userName).sortedByDescending { it.timestamp }.forEach { printLine(it.format()) }

    private fun Post.format(): String {
        val minutesAgo = Duration.between(now(clock), timestamp).toMinutes()
        return if (minutesAgo <= 1L) return "$message (1 minute ago)"
        else "$message ($minutesAgo minutes ago)"
    }
}