package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.ResetResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class ResetPasswordUseCases @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phone: String, otp: String,newPassword : String): NetworkResult<ResetResponse> {

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

        if(newPassword.isBlank()){
            return NetworkResult.Error(
                message = "New Password is Required"
            )
        }

        if(newPassword.length < 6){
            return NetworkResult.Error(
                message = "New Password at least of 6 digits"
            )
        }


        return authRepository.resetPassword(phone, otp,newPassword)

    }
}