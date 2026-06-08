package app.personal.mynote.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import app.personal.mynote.domain.usecases.CreateNoteUseCase
import app.personal.mynote.domain.usecases.DeleteUseCase
import app.personal.mynote.domain.usecases.EditUseCase
import app.personal.mynote.domain.usecases.ForgotOtpUseCases
import app.personal.mynote.domain.usecases.LoginUseCases
import app.personal.mynote.domain.usecases.NoteUseCase
import app.personal.mynote.domain.usecases.ProfileUseCases
import app.personal.mynote.domain.usecases.ResetPasswordUseCases
import app.personal.mynote.domain.usecases.SendOtpUseCase
import app.personal.mynote.domain.usecases.SignupUseCases
import app.personal.mynote.domain.usecases.UpdateNoteUseCase
import app.personal.mynote.domain.usecases.VerifyOtpUseCase
import app.personal.mynote.model.response.CreateNoteResponse
import app.personal.mynote.model.response.EditResponse
import app.personal.mynote.model.response.ForgotOtpResponse
import app.personal.mynote.model.response.LoginResponse
import app.personal.mynote.model.response.NotesResponse
import app.personal.mynote.model.response.ProfileResponse
import app.personal.mynote.model.response.ResetResponse
import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.model.response.SignUpResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.resource.NetworkResult
import app.personal.mynote.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val signupUseCase: SignupUseCases,
    private val loginUseCases: LoginUseCases,
    private val forgotOtpUseCases: ForgotOtpUseCases,
    private val resetPasswordUseCases: ResetPasswordUseCases,
    private val profileUseCases: ProfileUseCases,
    private val editUseCase: EditUseCase,
    private val createNoteResponse: CreateNoteResponse,
    private val getNoteUseCase: NoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    // send Otp
    private val _sendOtpState =
        MutableStateFlow<NetworkResult<SendOtpResponse>>(NetworkResult.Idle())
    val sendOtpState = _sendOtpState.asStateFlow()

    //verify-otp
    private val _verifyOtpState =
        MutableStateFlow<NetworkResult<VerifyOtpResponse>>(NetworkResult.Idle())
    val verifyOtpState = _verifyOtpState.asStateFlow()


    //signup
    private val _signupState =
        MutableStateFlow<NetworkResult<SignUpResponse>>(NetworkResult.Idle())
    val signupState = _signupState.asStateFlow()

    // login
    private val _loginState =
        MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Idle())
    val loginState = _loginState.asStateFlow()


    // forgot Otp
    private val _forgotOtpState =
        MutableStateFlow<NetworkResult<ForgotOtpResponse>>(NetworkResult.Idle())
    val forgotOtpState = _forgotOtpState.asStateFlow()


    // reset password
    private val _resetState =
        MutableStateFlow<NetworkResult<ResetResponse>>(NetworkResult.Idle())
    val resetState = _resetState.asStateFlow()

    // get Profile
    private val _getProfileState =
        MutableStateFlow<NetworkResult<ProfileResponse>>(NetworkResult.Idle())
    val getProfileState = _getProfileState.asStateFlow()

    // edit Profile
    private val _editProfileState =
        MutableStateFlow<NetworkResult<EditResponse>>(NetworkResult.Idle())
    val editProfileState = _editProfileState.asStateFlow()

    //logout
    private val _logoutState =
        MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Idle())

    val logoutState = _logoutState.asStateFlow()


    // create Note
    private val _createNoteState =
        MutableStateFlow<NetworkResult<CreateNoteResponse>>(NetworkResult.Idle())
    val createNoteState = _createNoteState.asStateFlow()


    // get Note
    private val _noteState =
        MutableStateFlow<NetworkResult<NotesResponse>>(NetworkResult.Idle())
    val noteState = _noteState.asStateFlow()


    private fun <T> executeApiCall(
        state: MutableStateFlow<NetworkResult<T>>,
        apiCall: suspend () -> NetworkResult<T>
    ) {

        viewModelScope.launch {

            state.value = NetworkResult.Loading()

            try {

                state.value = apiCall()

            } catch (e: Exception) {

                state.value = NetworkResult.Error(
                    message = e.localizedMessage
                        ?: "Unexpected error occurred"
                )
            }
        }
    }


    // send-otp
    fun sendOtp(phone: String) {

        executeApiCall(
            state = _sendOtpState
        ) {

            sendOtpUseCase(phone)

        }
    }


    //verify-otp
    fun verifyOtp(phone: String, otp: String) {

        executeApiCall(
            state = _verifyOtpState
        ) {

            val result = verifyOtpUseCase(phone, otp)

            if (result is NetworkResult.Success) {

                result.data?.token?.let { token ->

                    tokenManager.saveToken(token)
                }
            }

            result
        }
    }

    //signup
    fun signup(
        name: String,
        username: String,
        age: String,
        email: String,
        gender: String,
        password: String,
        confirmPassword: String
    ) {
        executeApiCall(
            state = _signupState
        ) {
            signupUseCase(name, username, age, email, gender, password, confirmPassword)
        }

    }


    //login
    fun login(phone: String, password: String) {

        executeApiCall(
            state = _loginState
        ) {

            val result = loginUseCases(phone, password)

            if (result is NetworkResult.Success) {

                result.data?.let { data ->

                    // Save token
                    data.token?.let { token ->
                        tokenManager.saveToken(token)
                    }

                    // Save user
                    data.user?.let { user ->
                        tokenManager.saveUser(user)
                    }
                }
            }

            result
        }
    }


    // forgot Otp
    fun forgotOtp(phone: String) {

        executeApiCall(
            state = _forgotOtpState
        ) {

            forgotOtpUseCases(phone)

        }
    }


    // reset Password
    fun resetPassword(phone: String, otp: String, newPassword: String) {

        executeApiCall(
            state = _resetState
        ) {

            resetPasswordUseCases(phone, otp, newPassword)
        }
    }

    //get Profile
    fun getProfile() {

        executeApiCall(
            state = _getProfileState
        ) {

            profileUseCases()
        }
    }

    // edit Profile

    fun editProfile(name: String, username: String, age: String, email: String, gender: String) {
        executeApiCall(
            state = _editProfileState
        ) {
            editUseCase(name, username, age, email, gender)
        }

    }

    //logout
    fun logout() {

        viewModelScope.launch {

            _logoutState.value = NetworkResult.Loading()

            try {

                tokenManager.clear()

                _sendOtpState.value = NetworkResult.Idle()
                _verifyOtpState.value = NetworkResult.Idle()
                _signupState.value = NetworkResult.Idle()
                _loginState.value = NetworkResult.Idle()
                _forgotOtpState.value = NetworkResult.Idle()
                _resetState.value = NetworkResult.Idle()
                _getProfileState.value = NetworkResult.Idle()
                _editProfileState.value = NetworkResult.Idle()

                _logoutState.value = NetworkResult.Success(Unit)

            } catch (e: Exception) {

                _logoutState.value = NetworkResult.Error(
                    message = e.localizedMessage ?: "Logout failed"
                )
            }
        }
    }




}