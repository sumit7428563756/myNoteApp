package app.personal.mynote.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.personal.mynote.presentation.authentication.ForgotScreen
import app.personal.mynote.presentation.authentication.LoginScreen
import app.personal.mynote.presentation.authentication.SendOtpScreen
import app.personal.mynote.presentation.authentication.SignUpScreen
import app.personal.mynote.presentation.home.HomeScreen
import app.personal.mynote.presentation.profile.EditScreen
import app.personal.mynote.presentation.profile.ProfileScreen
import app.personal.mynote.presentation.splash.SplashScreen
import app.personal.mynote.utils.TokenManager
import app.personal.mynote.utils.components.topbar.AppTopBar
import app.personal.mynote.utils.components.topbar.HomeTopBar
import app.personal.mynote.utils.components.topbar.ProfileBar
import java.nio.file.WatchEvent

@Composable
fun AppScaffold(
    navManager: NavigationManager,
    tokenManager: TokenManager
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        navManager.setController(navController)
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val hideTopBarRoutes = setOf(
        Routes.LOGIN,
        Routes.SIGN_UP,
        Routes.FORGOT,
        Routes.OTP_SCREEN,
        Routes.SPLASH
    )

    Scaffold(
        topBar = {

            when {
                currentRoute in hideTopBarRoutes -> {
                    // No TopBar
                }

                currentRoute == Routes.HOME -> {
                    HomeTopBar(
                        title = "MyNotes",
                        onProfileClick = {
                            navManager.navigate(Routes.PROFILE)
                        }
                    )
                }

                currentRoute == Routes.PROFILE -> {
                    ProfileBar(
                        title = "Profile",
                        showBackButton = true,
                        onEditClick = {
                            navManager.navigate(Routes.EDIT)
                        },
                        onBackClick = {
                            navManager.popBack()
                        }
                    )
                }

                else -> {
                    AppTopBar(
                        title = currentRoute.orEmpty(),
                        showBackButton = true,
                        onBackClick = {
                            navManager.popBack()
                        }
                    )
                }
            }
        },

        floatingActionButton = {
            if (currentRoute == Routes.HOME) {
                FloatingActionButton(
                    onClick = {
                        // FAB action
                    },
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 12.dp,
                        pressedElevation = 18.dp
                    ),
                    modifier = Modifier.padding(bottom = 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        },

        ) { padding ->

        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = Routes.SPLASH,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {

            composable(Routes.SPLASH) {
                SplashScreen(navManager, tokenManager)
            }

            composable(Routes.LOGIN) {
                LoginScreen(navManager)
            }

            composable(Routes.SIGN_UP) {
                SignUpScreen(navManager)
            }

            composable(Routes.FORGOT) {
                ForgotScreen(navManager)
            }

            composable(Routes.OTP_SCREEN) {
                SendOtpScreen(navManager)
            }

            composable(Routes.HOME) {
                HomeScreen(navManager)
            }

            composable(Routes.PROFILE) {
                ProfileScreen(navManager, tokenManager)
            }
            composable(Routes.EDIT) {
                EditScreen(navManager, tokenManager)
            }
        }
    }
}