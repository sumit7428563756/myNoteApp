package app.personal.mynote.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.personal.mynote.R
import app.personal.mynote.navigation.NavigationManager
import app.personal.mynote.navigation.Routes
import app.personal.mynote.utils.TokenManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navManager: NavigationManager,
    tokenManager: TokenManager
) {

    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF1E3ABA), Color(0xFF0B1530)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    LaunchedEffect(Unit) {

        delay(3000) // 3 seconds splash

        val token = tokenManager.getToken()

        if (!token.isNullOrEmpty()) {
            navManager.navigateAndClear(Routes.HOME)
        } else {
            navManager.navigateAndClear(Routes.LOGIN)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(R.drawable.notelogo),
                contentDescription = "logo",
                modifier = Modifier.size(200.dp),
                tint = Color.Unspecified)
        }

    }
}