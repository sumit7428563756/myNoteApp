package app.personal.mynote.network.service

import app.personal.mynote.model.request.CreateNoteRequest
import app.personal.mynote.model.request.DeleteRequest
import app.personal.mynote.model.request.EditProfileRequest
import app.personal.mynote.model.request.ForgotOtpRequest
import app.personal.mynote.model.request.LoginRequest
import app.personal.mynote.model.request.PasswordRequest
import app.personal.mynote.model.request.ProfileRequest
import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.model.request.SendOtpRequest
import app.personal.mynote.model.request.SignupRequest
import app.personal.mynote.model.request.UpdateNoteRequest
import app.personal.mynote.model.request.VerifyOtpRequest
import app.personal.mynote.model.response.CreateNoteResponse
import app.personal.mynote.model.response.DeleteResponse
import app.personal.mynote.model.response.EditResponse
import app.personal.mynote.model.response.ForgotOtpResponse
import app.personal.mynote.model.response.LoginResponse
import app.personal.mynote.model.response.NotesResponse
import app.personal.mynote.model.response.ProfileResponse
import app.personal.mynote.model.response.ResetResponse
import app.personal.mynote.model.response.SignUpResponse
import app.personal.mynote.model.response.UpdateNoteResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.consts.ApiConstant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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


    //Login
    @POST(ApiConstant.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>



    // forgot Otp
    @POST(ApiConstant.FORGOT_OTP)
    suspend fun forgotOtp(
        @Body request: ForgotOtpRequest
    ): Response<ForgotOtpResponse>




    // reset Password
    @POST(ApiConstant.FORGOT_PASSWORD)
    suspend fun forgotPassword(
        @Body request: PasswordRequest
    ): Response<ResetResponse>



    // get Profile
    @AuthRequired
    @GET(ApiConstant.GET_PROFILE)
    suspend fun getProfile(): Response<ProfileResponse>



    // edit Profile
    @AuthRequired
    @PATCH(ApiConstant.EDIT_PROFILE)
    suspend fun editProfile(
        @Body request: EditProfileRequest
    ): Response<EditResponse>

    // create Note
    @AuthRequired
    @POST(ApiConstant.CREATE_NOTE)
    suspend fun createNote(
        @Body request: CreateNoteRequest
    ): Response<CreateNoteResponse>

    // get Notes
    @AuthRequired
    @GET(ApiConstant.GET_NOTE)
    suspend fun getNotes(): Response<NotesResponse>


    // update Note
    @AuthRequired
    @PATCH(ApiConstant.UPDATE_NOTE)
    suspend fun updateNote(
        @Body request: UpdateNoteRequest
    ): Response<UpdateNoteResponse>



    // delete Note
    @AuthRequired
    @DELETE(ApiConstant.DELETE_NOTE)
    suspend fun deleteNote(
        @Body request: DeleteRequest
    ): Response<DeleteResponse>





}