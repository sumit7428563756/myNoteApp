package app.personal.mynote.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.personal.mynote.domain.viewmodel.AuthViewModel
import app.personal.mynote.navigation.NavigationManager

@Composable
fun HomeScreen(
    navigationManager: NavigationManager,
    viewModel: AuthViewModel = hiltViewModel()
) {



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF4F2FA)
            )
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {





    }


}