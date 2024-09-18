class AuthRepository {

    private val mockUser = User("user123", "password123")

    fun login(email: String, password: String): Result<User> {
        return if (email == mockUser.email && password == mockUser.password) {
            Result.Success(mockUser)
        } else {
            Result.Error("Usuário ou senha incorretos")
        }
    }
}