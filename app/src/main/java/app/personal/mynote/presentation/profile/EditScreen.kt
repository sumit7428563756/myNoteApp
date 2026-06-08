package app.personal.mynote.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.personal.mynote.domain.viewmodel.AuthViewModel
import app.personal.mynote.model.response.EditResponse
import app.personal.mynote.model.response.ProfileResponse
import app.personal.mynote.navigation.NavigationManager
import app.personal.mynote.navigation.Routes
import app.personal.mynote.network.resource.NetworkResult
import app.personal.mynote.utils.TokenManager
import app.personal.mynote.utils.components.AppTextField
import app.personal.mynote.utils.components.GradientLoadingButton

@Composable
fun EditScreen(
    navigationManager: NavigationManager,
    tokenManager: TokenManager,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    val profileState by viewModel.getProfileState.collectAsStateWithLifecycle()
    val editState by viewModel.editProfileState.collectAsStateWithLifecycle()


    val profile = (profileState as? NetworkResult.Success)?.data

    // Only SIDE EFFECTS here
    LaunchedEffect(profileState) {
        when (profileState) {

            is NetworkResult.Error -> {
                Toast.makeText(
                    context,
                    (profileState as NetworkResult.Error).message,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> Unit
        }
    }


    LaunchedEffect(editState) {

        when (editState) {

            is NetworkResult.Success -> {

                Toast.makeText(
                    context,
                    "Profile Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                navigationManager.navigateAndClear(Routes.PROFILE)

            }

            is NetworkResult.Error -> {

                Toast.makeText(
                    context,
                    (editState as NetworkResult.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    // UI ALWAYS HERE
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F2FA)),
        contentAlignment = Alignment.Center
    ) {

        when (profileState) {

            is NetworkResult.Loading -> {
                CircularProgressIndicator(color = Color.Black)
            }

            is NetworkResult.Success -> {
                EditContent(
                    profile = profile,
                    editState = editState,
                    viewModel = viewModel
                )
            }

            is NetworkResult.Error -> {
                Text("Something went wrong")
            }

            else -> Unit
        }
    }
}

@Composable
fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        AppTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = label,
            readOnly = false
        )

        Spacer(modifier = Modifier.height(14.dp))
    }
}


@Composable
fun EditContent(
    profile: ProfileResponse?,
    editState: NetworkResult<EditResponse>,
    viewModel: AuthViewModel
) {

    var name by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(profile) {
        profile?.userData?.let {
            name = it.name ?: ""
            username = it.username ?: ""
            age = it.age ?: ""
            email = it.email ?: ""
            gender = it.gender ?: ""
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 22.dp)
        ) {

            EditField("Name", name) { name = it }
            EditField("Username", username) { username = it }
            EditField("Age", age) { age = it }
            EditField("Email", email) { email = it }
            EditField("Gender", gender) { gender = it }

            GradientLoadingButton(
                text = "Update and Save",
                isLoading = editState is NetworkResult.Loading,
                onClick = {
                    viewModel.editProfile(
                        name = name,
                        username = username,
                        age = age,
                        email = email,
                        gender = gender
                    )
                }
            )
        }
    }
}