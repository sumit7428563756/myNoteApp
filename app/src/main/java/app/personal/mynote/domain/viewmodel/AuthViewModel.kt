package app.personal.mynote.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import app.personal.mynote.domain.usecases.SendOtpUseCase
import app.personal.mynote.domain.usecases.VerifyOtpUseCase
import app.personal.mynote.model.response.SendOtpResponse
import app.personal.mynote.model.response.VerifyOtpResponse
import app.personal.mynote.network.resource.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {

    // send Otp
    private val _sendOtpState =
        MutableStateFlow<NetworkResult<SendOtpResponse>>(NetworkResult.Idle())
    val sendOtpState = _sendOtpState.asStateFlow()

    //verify-otp
    private val _verifyOtpState =
        MutableStateFlow<NetworkResult<VerifyOtpResponse>>(NetworkResult.Idle())
    val verifyOtpState = _verifyOtpState.asStateFlow()

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


    fun sendOtp(phone: String) {

        executeApiCall(
            state = _sendOtpState
        ) {

            sendOtpUseCase(phone)

        }
    }

    fun verifyOtp(phone: String, otp: String) {

        executeApiCall(
            state = _verifyOtpState
        ) {

            verifyOtpUseCase(phone, otp)

        }
    }

}