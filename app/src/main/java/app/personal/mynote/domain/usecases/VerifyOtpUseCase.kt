package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phone: String, otp: String): NetworkResult<VerifyOtpResponse> {

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

        if (otp.isBlank()) {
            return NetworkResult.Error(
                message = "otp is required"
            )
        }

        if (otp.length < 6) {
            return NetworkResult.Error(
                message = "Invalid otp"
            )
        }


        return authRepository.verifyOtp(phone, otp)

    }
}