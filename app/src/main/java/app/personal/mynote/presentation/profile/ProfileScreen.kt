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
import app.personal.mynote.model.response.ProfileResponse
import app.personal.mynote.navigation.NavigationManager
import app.personal.mynote.navigation.Routes
import app.personal.mynote.network.resource.NetworkResult
import app.personal.mynote.utils.TokenManager
import app.personal.mynote.utils.components.AppTextField
import app.personal.mynote.utils.components.GradientLoadingButton
import app.personal.mynote.utils.components.LogoutCardButton

@Composable
fun ProfileScreen(
    navigationManager: NavigationManager,
    tokenManager: TokenManager,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    val profileState by viewModel.getProfileState.collectAsStateWithLifecycle()
    val logoutState by viewModel.logoutState.collectAsStateWithLifecycle()

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

    LaunchedEffect(logoutState) {

        when (logoutState) {

            is NetworkResult.Success -> {
                navigationManager.navigateAndClear(Routes.LOGIN)
            }

            is NetworkResult.Error -> {
                Toast.makeText(context, "Logout failed", Toast.LENGTH_SHORT).show()
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
                ProfileContent(profile,viewModel)
            }

            is NetworkResult.Error -> {
                Text("Something went wrong")
            }

            else -> Unit
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String
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
            onValueChange = {},
            placeholder = label,
            readOnly = true
        )

        Spacer(modifier = Modifier.height(14.dp))
    }
}


@Composable
fun ProfileContent(profile: ProfileResponse?,viewModel: AuthViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize().padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

                ProfileField(label = "Name", value = profile?.userData?.name ?: "")
                ProfileField(label = "Username", value = profile?.userData?.username ?: "")
                ProfileField(label = "Age", value = profile?.userData?.age ?: "")
                ProfileField(label = "Email", value = profile?.userData?.email ?: "")
                ProfileField(label = "Gender", value = profile?.userData?.gender ?: "")

            }
        }

         Spacer(modifier = Modifier.height(14.dp))
        LogoutCardButton(onClick = {viewModel.logout()})
    }
}