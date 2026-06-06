package app.personal.mynote.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.personal.mynote.domain.viewmodel.AuthViewModel
import app.personal.mynote.navigation.NavigationManager
import app.personal.mynote.navigation.Routes
import app.personal.mynote.network.resource.NetworkResult
import app.personal.mynote.utils.components.AppTextField
import app.personal.mynote.utils.components.GradientLoadingButton

@Composable
fun LoginScreen(
    navigationManager: NavigationManager,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var phone by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    val loginState by viewModel
        .loginState
        .collectAsStateWithLifecycle()



    LaunchedEffect(loginState) {

        when (loginState) {

            is NetworkResult.Success -> {

                Toast.makeText(
                    context,
                    "Login successfully",
                    Toast.LENGTH_SHORT
                ).show()

                navigationManager.navigateAndClear(Routes.HOME)

            }

            is NetworkResult.Error -> {

                Toast.makeText(
                    context,
                    (loginState as NetworkResult.Error).message,
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

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
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
                        text = "Login",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF111111)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Enter Phone Number and Password to Continue",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    AppTextField(
                        value = phone,
                        onValueChange = {
                            phone = it.filter { char -> char.isDigit() }
                                .take(10)
                        },
                        placeholder = "Mobile Number",
                        keyboardType = KeyboardType.Number,
                        maxLength = 10
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    AppTextField(
                        value = password,
                        onValueChange = {
                            password = it.filter { char -> char.isDigit() }
                                .take(10)
                        },
                        placeholder = "Enter Password",
                        keyboardType = KeyboardType.Number,
                    )

                    Spacer(modifier = Modifier.height(22.dp))


                    GradientLoadingButton(
                        text = "Continue",
                        isLoading = loginState is NetworkResult.Loading,
                        onClick = {
                            viewModel.login(phone, password)
                        }
                    )


                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val loginText = buildAnnotatedString {
                append("Already have an account? ")
                pushStringAnnotation(tag = "SIGN", annotation = "Sign in")
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("Sign in")
                }
                pop()
            }

            ClickableText(
                text = loginText,
                onClick = { offset ->
                    loginText.getStringAnnotations(tag = "SIGN", start = offset, end = offset)
                        .firstOrNull()?.let {
                            // navigate to login screen
                            navigationManager.navigate(Routes.OTP_SCREEN)
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            )

        }
    }

}


