package app.personal.mynote.network.repository

import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.network.resource.NetworkResult

interface AuthRepository{

    suspend fun sendOtp( phone : String ) : NetworkResult<SendOtpResponse>


}