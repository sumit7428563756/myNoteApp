package app.personal.mynote.domain.repositoryImpl

import app.personal.mynote.model.request.SendOtpRequest
import app.personal.mynote.model.request.VerifyOtpRequest
import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import app.personal.mynote.network.safeCall.SafeApiCall
import app.personal.mynote.network.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImp @Inject constructor(
    private val api: ApiService,
    private val safeApiCall: SafeApiCall
) : AuthRepository {

    //send otp
    override suspend fun sendOtp(phone: String): NetworkResult<SendOtpResponse> {
        return safeApiCall.execute {

            api.sendOtp(
                SendOtpRequest(phone)
            )
        }
    }

    //verify otp
    override suspend fun verifyOtp(
        phone: String,
        otp: String
    ): NetworkResult<VerifyOtpResponse> {
     return safeApiCall.execute {
         api.verifyOtp(
             VerifyOtpRequest(phone,otp)
         )
     }
    }




}