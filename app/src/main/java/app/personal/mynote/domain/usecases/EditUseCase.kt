package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.EditResponse
import app.personal.mynote.model.response.SignUpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class EditUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        name: String,
        username: String,
        age: String,
        email: String,
        gender: String,
    ): NetworkResult<EditResponse>   {

        if (
            name.isBlank() ||
            username.isBlank() ||
            age.isBlank() ||
            email.isBlank() ||
            gender.isBlank()
        ) {

            return NetworkResult.Error(
                message = "All fields are required"
            )
        }



        return repository.editProfile(
            name = name,
            username = username,
            age = age,
            email = email,
            gender = gender
        )
    }
}