package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.LoginResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class LoginUseCases @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phone: String, password: String): NetworkResult<LoginResponse> {

        if (phone.isBlank()) {
            return NetworkResult.Error(
                message = "Phone number is required"
            )
        }

        if (phone.length < 10) {
            return NetworkResult.Error(
                message = "Invalid phone number"
            )
        }

        if (password.isBlank()) {
            return NetworkResult.Error(
                message = "Password is Required"
            )
        }

        if (password.length < 6) {
            return NetworkResult.Error(
                message = "Password must be at least 6 digit"
            )
        }


        return authRepository.login(phone, password)

    }
}