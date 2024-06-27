package com.dm.berxley.chatapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.chatapp.screens.Screen
import com.dm.berxley.chatapp.screens.SignInScreen
import com.dm.berxley.chatapp.screens.SignUpScreen
import com.dm.berxley.chatapp.ui.theme.ChatAppTheme
import com.dm.berxley.chatapp.viewmodels.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(navController = navController, authViewModel = authViewModel)
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, authViewModel: AuthViewModel) {

    NavHost(navController = navController, startDestination = Screen.SignUpScreen.route) {

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) })

        }
        composable(Screen.LoginScreen.route) {
            SignInScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = { navController.navigate(Screen.SignUpScreen.route) },
                onSignInSuccess = { navController.navigate(Screen.ChatRoomsScreen.route) }
            )
        }

    }
}