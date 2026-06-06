package app.personal.mynote.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.personal.mynote.domain.viewmodel.AuthViewModel
import app.personal.mynote.navigation.NavigationManager
import app.personal.mynote.navigation.Routes
import app.personal.mynote.network.resource.NetworkResult
import app.personal.mynote.utils.components.AppTextField
import app.personal.mynote.utils.components.GradientLoadingButton

@Composable
fun SignUpScreen(
    navigationManager: NavigationManager,
    viewModel: AuthViewModel = hiltViewModel()
) {


    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }


    val signupState by viewModel
        .signupState
        .collectAsStateWithLifecycle()


    LaunchedEffect(signupState) {

        when (signupState) {

            is NetworkResult.Success -> {
                Toast.makeText(
                    context,
                    "Signup successful",
                    Toast.LENGTH_SHORT
                ).show()

                navigationManager.navigateAndClear(Routes.LOGIN  )
            }

            is NetworkResult.Error -> {
                Toast.makeText(
                    context,
                    (signupState as NetworkResult.Error).message,
                    Toast.LENGTH_LONG
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
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111111)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Fill All details Carefully",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(26.dp))

                // NAME
                AppTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Enter Name"
                )

                Spacer(modifier = Modifier.height(10.dp))


                // USERNAME
                AppTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = "Enter Username"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // AGE (numbers only)
                AppTextField(
                    value = age,
                    onValueChange = {
                        age = it.filter { c -> c.isDigit() }.take(3)
                    },
                    placeholder = "Enter Age",
                    keyboardType = KeyboardType.Number,
                    maxLength = 3
                )

                Spacer(modifier = Modifier.height(10.dp))

                // EMAIL
                AppTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter Email",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(10.dp))

                // GENDER
                AppTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    placeholder = "Enter Gender"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // PASSWORD
                AppTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Enter Password",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // CONFIRM PASSWORD
                AppTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Confirm Password",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                GradientLoadingButton(
                    text = "Continue",
                    isLoading = signupState is NetworkResult.Loading,
                    onClick = {
                        viewModel.signup(name,username,age,email,gender,password,confirmPassword)
                    }
                )
            }
        }
    }
}