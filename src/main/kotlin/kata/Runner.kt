package kata

import java.time.Clock

private val inMemoryDB = object: Posts, Followers {
    val posts = mutableListOf<Post>()
    val followers = mutableMapOf<String, MutableList<String>>()
    override fun add(post: Post): Unit = run { posts.add(post) }
    override fun findBy(userName: String): List<Post> = posts.filter { it.username == userName }
    override fun add(follower: String, followed: String): Unit =
        run { followers.getOrPut(follower) { mutableListOf() }.add(followed) }
    override fun findFollowedBy(follower: String): List<String> = followers.getOrDefault(follower, mutableListOf())
}

fun main(args: Array<String>) {
    println("Social network - 'quit' to exit")

    val socialNetwork = SocialNetwork(inMemoryDB,inMemoryDB,{ println(it) },Clock.systemDefaultZone())

    while(true) {
        print("> ")
        val input = readlnOrNull()
        if (input.equals("quit")) break
        else if (input != null)socialNetwork.submitCommand(input)
    }
}
