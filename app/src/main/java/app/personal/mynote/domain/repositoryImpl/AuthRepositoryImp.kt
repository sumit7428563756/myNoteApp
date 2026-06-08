package app.personal.mynote.domain.repositoryImpl

import app.personal.mynote.model.request.CreateNoteRequest
import app.personal.mynote.model.request.DeleteRequest
import app.personal.mynote.model.request.EditProfileRequest
import app.personal.mynote.model.request.ForgotOtpRequest
import app.personal.mynote.model.request.LoginRequest
import app.personal.mynote.model.request.PasswordRequest
import app.personal.mynote.model.request.ProfileRequest
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
import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.model.response.SignUpResponse
import app.personal.mynote.model.response.UpdateNoteResponse
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
                VerifyOtpRequest(phone, otp)
            )
        }
    }

    //signup
    override suspend fun signup(
        name: String,
        username: String,
        age: String,
        email: String,
        gender: String,
        password: String,
        confirmPassword : String
    ): NetworkResult<SignUpResponse> {
        return safeApiCall.execute {
            api.signup(
                SignupRequest(
                    name, username, email, age, gender, password, confirmPassword
                )
            )
        }
    }


    // login
    override suspend fun login(
        phone: String,
        password: String
    ): NetworkResult<LoginResponse> {
        return safeApiCall.execute {
            api.login(
                LoginRequest(phone, password)
            )
        }
    }

    // forgot Otp
    override suspend fun forgotOtp(phone: String): NetworkResult<ForgotOtpResponse> {
       return safeApiCall.execute {
            api.forgotOtp(
                ForgotOtpRequest(phone)
            )
       }
    }

    //reset password
    override suspend fun resetPassword(
        phone: String,
        otp: String,
        newPassword: String
    ): NetworkResult<ResetResponse> {
        return safeApiCall.execute {
            api.forgotPassword(
                PasswordRequest(phone,otp,newPassword)
            )
        }
    }


    //get Profile
    override suspend fun getProfile(): NetworkResult<ProfileResponse> {
        return safeApiCall.execute {
            api.getProfile()
        }
    }

    //edit Profile
    override suspend fun editProfile(
        name: String,
        username: String,
        age: String,
        email: String,
        gender: String
    ): NetworkResult<EditResponse> {
        return safeApiCall.execute {
            api.editProfile(
                EditProfileRequest(name,username,age,email,gender)
            )
        }
    }

    override suspend fun createNote(
        img: String,
        title: String,
        description: String
    ): NetworkResult<CreateNoteResponse> {
        return safeApiCall.execute {
            api.createNote(
                CreateNoteRequest(img,title,description)
            )
        }
    }

    override suspend fun getNotes(): NetworkResult<NotesResponse> {
        return safeApiCall.execute {
            api.getNotes()
        }
    }

    override suspend fun updateNote(
        id: String,
        img: String,
        title: String,
        description: String
    ): NetworkResult<UpdateNoteResponse> {
        return safeApiCall.execute {
            api.updateNote(
                UpdateNoteRequest(id,img,title,description)
            )
        }
    }

    override suspend fun deleteNote(id: String): NetworkResult<DeleteResponse> {
        return safeApiCall.execute {
            api.deleteNote(
                DeleteRequest(id)
            )
        }
    }


}