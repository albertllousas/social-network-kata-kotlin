package kata

class SocialNetwork(private val posts: Posts) {
    fun submitCommand(command: String) {
        val (userName, post) = command.split(" -> ")
        posts.add(userName, post)
    }
}
