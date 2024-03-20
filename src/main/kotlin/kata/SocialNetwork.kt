package kata

import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime.now

class SocialNetwork(
    private val posts: Posts,
    private val followers: Followers,
    private val printLine: (String) -> Unit,
    private val clock: Clock,
) {

    fun submitCommand(command: String) = when {
        command.contains(" -> ") -> command.split(" -> ").let { addPost(it[0], it[1]) }
        command.contains(" follows ") -> command.split(" follows ").let { addFollower(it[0], it[1]) }
        command.endsWith(" wall") -> viewWall(command.dropLast(5))
        else -> viewUserTimeline(command)
    }

    private fun addPost(userName: String, message: String) = posts.add(userName, Post(message, now(clock)))

    private fun addFollower(follower: String, followed: String) = followers.add(follower, followed)

    private fun viewUserTimeline(userName: String) =
        posts.findBy(userName).sortedByDescending { it.timestamp }.forEach { printLine(it.format()) }

    private fun viewWall(userName: String) {
        val users = followers.findFollowedBy(userName) + userName
        val posts = users.flatMap { user -> posts.findBy(user).map { Pair(user, it) } }
        return posts.sortedByDescending { it.second.timestamp }.forEach { printLine(it.second.format(it.first)) }
    }

    private fun Post.format(userName: String? = null) =
        Pair(Duration.between(now(clock), timestamp), userName?.let { "$userName - " } ?: "")
            .let { (elapsedTime, user) ->
                when {
                    elapsedTime.toMinutes() < 1L -> "$user$message (${elapsedTime.seconds} seconds ago)"
                    elapsedTime.toMinutes() == 1L -> "$user$message (1 minute ago)"
                    else -> "$user$message (${elapsedTime.toMinutes()} minutes ago)"
                }
            }
}
