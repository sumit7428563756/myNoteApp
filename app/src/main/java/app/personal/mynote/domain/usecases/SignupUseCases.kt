package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.SignUpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class SignupUseCases @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        name: String,
        username: String,
        age: String,
        email: String,
        gender: String,
        password: String,
        confirmPassword: String
    ): NetworkResult<SignUpResponse> {

        if (
            name.isBlank() ||
            username.isBlank() ||
            age.isBlank() ||
            email.isBlank() ||
            gender.isBlank() ||
            password.isBlank() ||
            confirmPassword.isBlank()
        ) {

            return NetworkResult.Error(
                message = "All fields are required"
            )
        }



        if (password.length < 6 || password.length > 10) {

            return NetworkResult.Error(
                message = "Password must be between 6 and 10 characters"
            )
        }

        if (password != confirmPassword) {

            return NetworkResult.Error(
                message = "Passwords do not match"
            )
        }

        return repository.signup(
            name = name,
            username = username,
            age = age,
            email = email,
            gender = gender,
            password = password,
            confirmPassword = confirmPassword
        )
    }
}