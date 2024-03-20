package kata

import java.time.LocalDateTime

interface Posts {
    fun add(post: Post)
    fun findBy(userName: String): List<Post>
}

data class Post(val username: String, val message: String, val timestamp: LocalDateTime)
