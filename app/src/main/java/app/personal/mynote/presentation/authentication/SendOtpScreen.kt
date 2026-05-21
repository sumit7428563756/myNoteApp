package app.personal.mynote.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.personal.mynote.domain.viewmodel.AuthViewModel
import app.personal.mynote.network.resource.NetworkResult
import app.personal.mynote.utils.components.FullWidthNumberField
import app.personal.mynote.utils.components.GradientLoadingButton

@Composable
fun SendOtpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    var phone by rememberSaveable {
        mutableStateOf("")
    }

    var otp by rememberSaveable {
        mutableStateOf("")
    }

    val sendOtpState by viewModel
        .sendOtpState
        .collectAsStateWithLifecycle()

    val verifyOtpState by viewModel
        .verifyOtpState
        .collectAsStateWithLifecycle()

    LaunchedEffect(sendOtpState) {

        when (sendOtpState) {

            is NetworkResult.Success -> {

                // success
                Toast.makeText(context, "Otp sent successfully", Toast.LENGTH_LONG).show()
            }

            is NetworkResult.Error -> {

                // show snackbar
                Toast.makeText(
                    context,
                    (sendOtpState as NetworkResult.Error).message,
                    Toast.LENGTH_LONG
                ).show()

            }

            else -> Unit
        }
    }


    LaunchedEffect(verifyOtpState) {

        when (verifyOtpState) {

            is NetworkResult.Success -> {

                Toast.makeText(
                    context,
                    "OTP verified successfully",
                    Toast.LENGTH_SHORT
                ).show()

            }

            is NetworkResult.Error -> {

                Toast.makeText(
                    context,
                    (verifyOtpState as NetworkResult.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF4F2FA)
            ),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp,
                        vertical = 22.dp
                    )
            ) {

                Text(
                    text = "Phone verification",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111111)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "We'll text you a one-time code.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(26.dp))

                FullWidthNumberField(
                    value = phone,
                    onValueChange = {
                        if (it.length <= 10) {

                            phone = it.filter { char ->
                                char.isDigit()
                            }
                        }
                    },
                    placeholder = "Mobile Number",
                    maxLength = 10,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    FullWidthNumberField(
                        value = otp,
                        onValueChange = {
                            if (it.length <= 6) {

                                otp = it.filter { char ->
                                    char.isDigit()
                                }
                            }
                        },
                        placeholder = "Enter Otp",
                        maxLength = 6,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {

                            viewModel.sendOtp(phone)

                        },
                        enabled = sendOtpState !is NetworkResult.Loading,
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(
                            horizontal = 18.dp,
                            vertical = 14.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .height(56.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFFB03CF7),
                                        Color(0xFF4B8BFF)
                                    )
                                ),
                                shape = RoundedCornerShape(14.dp)
                            )
                    ) {

                        if (sendOtpState is NetworkResult.Loading) {

                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )

                        } else {

                            Text(
                                text = "Send OTP",
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))


                GradientLoadingButton(
                    text = "Continue",
                    isLoading = verifyOtpState is NetworkResult.Loading,
                    onClick = {
                        viewModel.verifyOtp(phone, otp)
                    }
                )

            }
        }
    }
}

