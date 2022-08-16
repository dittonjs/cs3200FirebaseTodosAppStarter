package com.usu.firebasetodosapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.usu.firebasetodosapplication.ui.repositories.UserRepository

class RootNavigationViewModel(application: Application): AndroidViewModel(application) {
    fun signOutUser() {
        UserRepository.signOutUser()
    }
}