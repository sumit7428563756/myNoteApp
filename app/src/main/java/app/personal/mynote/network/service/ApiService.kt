package app.personal.mynote.network.service

import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.model.request.SendOtpRequest
import app.personal.mynote.model.request.SignupRequest
import app.personal.mynote.model.request.VerifyOtpRequest
import app.personal.mynote.model.response.SignUpResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.consts.ApiConstant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthRequired
interface ApiService {


    // Send Otp
    @POST(ApiConstant.SEND_OTP)
    suspend fun sendOtp(
        @Body request: SendOtpRequest
    ): Response<SendOtpResponse>

    //verify otp
    @POST(ApiConstant.VERIFY_OTP)
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<VerifyOtpResponse>


    //signup
    @AuthRequired
    @POST(ApiConstant.SIGNUP)
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignUpResponse>



}