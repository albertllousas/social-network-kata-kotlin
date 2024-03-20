package kata

import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime

object PostFormatter {
    fun format(post: Post, clock: Clock, showUser: Boolean): String =
        Pair(Duration.between(LocalDateTime.now(clock), post.timestamp), if (showUser) "${post.username} - " else "")
            .let { (elapsedTime, user) ->
                when {
                    elapsedTime.toMinutes() < 1L -> "$user${post.message} (${elapsedTime.seconds} seconds ago)"
                    elapsedTime.toMinutes() == 1L -> "$user${post.message} (1 minute ago)"
                    else -> "$user${post.message} (${elapsedTime.toMinutes()} minutes ago)"
                }
            }
}
