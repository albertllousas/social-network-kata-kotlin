package kata

import java.time.LocalDateTime

interface Posts {
    fun add(userName: String, post: Post)
    fun findBy(userName: String): List<Post>
}

data class Post(val message: String, val timestamp: LocalDateTime)
