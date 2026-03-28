package com.example.vaultx.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vaultx.Repository.PasswordRepository

class PasswordViewModelFactory(private val repository: PasswordRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordViewModel(repository) as T
    }
}