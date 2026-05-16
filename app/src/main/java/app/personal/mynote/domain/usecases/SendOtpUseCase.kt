package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(phone: String): NetworkResult<SendOtpResponse> {

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

        return repository.sendOtp(phone)

    }

}