// Task4.kt — Пользователь и фабрика (companion object)

class User private constructor(
    val username: String,
    val email: String,
    val age: Int
) {
    companion object {
        const val MIN_AGE = 0
        const val MAX_AGE = 150

        fun createGuest(): User = User("guest", "guest@example.com", 0)

        fun fromMap(map: Map<String, String>): User? {
            val username = map["username"] ?: return null
            val email = map["email"] ?: return null
            val age = map["age"]?.toIntOrNull() ?: return null
            if (age !in MIN_AGE..MAX_AGE) return null
            return User(username, email, age)
        }
    }

    override fun toString(): String = "User($username, $email, age=$age)"
}

fun main() {
    val guest = User.createGuest()
    println("Гость: $guest")

    val validMap = mapOf(
        "username" to "alex",
        "email" to "alex@mail.ru",
        "age" to "25"
    )
    val userFromMap = User.fromMap(validMap)
    println("Из корректной карты: $userFromMap")

    val invalidMap = mapOf(
        "username" to "bob",
        "age" to "30"
    )
    val invalidUser = User.fromMap(invalidMap)
    println("Из некорректной карты: $invalidUser")

    val badAgeMap = mapOf(
        "username" to "carol",
        "email" to "carol@mail.ru",
        "age" to "abc"
    )
    println("С невалидным возрастом: ${User.fromMap(badAgeMap)}")
}