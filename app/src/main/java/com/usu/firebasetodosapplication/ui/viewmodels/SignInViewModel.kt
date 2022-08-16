package com.usu.firebasetodosapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.usu.firebasetodosapplication.ui.repositories.SignInException
import com.usu.firebasetodosapplication.ui.repositories.UserRepository

class SignInScreenState {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)
}

class SignInViewModel(application: Application): AndroidViewModel(application) {
    val uiState = SignInScreenState()

    suspend fun signIn() {
        // clear existing errors
        uiState.emailError = false
        uiState.passwordError = false
        uiState.errorMessage = ""
        if (uiState.email.isEmpty()) {
            uiState.emailError = true
            uiState.errorMessage = "Email cannot be blank"
            return
        }

        if (uiState.password.isEmpty()) {
            uiState.passwordError = true
            uiState.errorMessage = "Password cannot be blank."
            return
        }
        // TODO: create login user
        try {
            UserRepository.loginUser(uiState.email, uiState.password)
            uiState.loginSuccess = true
        } catch (e: SignInException) {
            uiState.errorMessage = e.message ?: "Something went wrong. Please try again."
        }
    }
}