package app.personal.mynote.network.repository

import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.resource.NetworkResult

interface AuthRepository{

    //send otp
    suspend fun sendOtp( phone : String ) : NetworkResult<SendOtpResponse>

    // verify otp
    suspend fun verifyOtp( phone : String , otp : String ) : NetworkResult<VerifyOtpResponse>
}