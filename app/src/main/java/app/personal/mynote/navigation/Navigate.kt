package app.personal.mynote.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.personal.mynote.presentation.authentication.SignUpScreen
import app.personal.mynote.presentation.authentication.LoginScreen
import app.personal.mynote.presentation.authentication.SendOtpScreen

@Composable
fun Navigate(
    navManager: NavigationManager
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        navManager.setController(navController)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        enterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
        }
    ) {
        composable(Routes.OTP_SCREEN) {
            SendOtpScreen(navManager)
        }

        composable(Routes.SIGN_UP) {
            SignUpScreen(navManager)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navManager)
        }
    }
}