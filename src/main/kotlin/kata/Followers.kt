package kata

interface Followers {
    fun add(follower: String, followed: String)
    fun findFollowedBy(follower: String) : List<String>
}
