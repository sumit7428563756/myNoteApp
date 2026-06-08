package app.personal.mynote.network.repository

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
import app.personal.mynote.network.resource.NetworkResult

interface AuthRepository {

    //send otp
    suspend fun sendOtp(phone: String): NetworkResult<SendOtpResponse>

    // verify otp
    suspend fun verifyOtp(phone: String, otp: String): NetworkResult<VerifyOtpResponse>

    //signup
    suspend fun signup(
        name: String,
        username: String,
        age: String,
        email: String,
        gender: String,
        password: String,
        confirmPassword : String
    ): NetworkResult<SignUpResponse>

    // login
    suspend fun login(phone: String, password: String): NetworkResult<LoginResponse>


    // forgot otp
    suspend fun forgotOtp(phone: String): NetworkResult<ForgotOtpResponse>

    // reset password
    suspend fun resetPassword(phone: String, otp: String,newPassword : String): NetworkResult<ResetResponse>

    // get profile
    suspend fun getProfile(): NetworkResult<ProfileResponse>

    // edit Profile
    suspend fun editProfile(
        name: String,
        username: String,
        age: String,
        email: String,
        gender: String
    ): NetworkResult<EditResponse>


    // create Note
    suspend fun createNote(img : String, title : String, description : String ): NetworkResult<CreateNoteResponse>


    // get Notes
    suspend fun getNotes(): NetworkResult<NotesResponse>


    // update notes
    suspend fun updateNote(id : String ,img : String, title : String, description : String ): NetworkResult<UpdateNoteResponse>


    //delete Notes
    suspend fun deleteNote(id : String): NetworkResult<DeleteResponse>



}