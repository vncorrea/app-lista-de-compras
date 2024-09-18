class AuthRepository {

    private val mockUser = User("admin@admin.com", "admin");

    fun login(email: String, password: String) {
        if (email == mockUser.email && password == mockUser.password) {
            Result.Success(mockUser);
        }

        Result.Error("Email ou senha inválidos");
    }
}