package app.personal.mynote.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import app.personal.mynote.domain.usecases.SendOtpUseCase
import app.personal.mynote.domain.usecases.SignupUseCases
import app.personal.mynote.domain.usecases.VerifyOtpUseCase
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
        ){
          signupUseCase(name,username,age,email,gender,password,confirmPassword)
        }

    }


}