package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.ProfileResponse
import app.personal.mynote.model.response.SignUpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class ProfileUseCases    @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): NetworkResult<ProfileResponse> {
        return repository.getProfile()
    }
}