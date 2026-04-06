package com.example.vaultx.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultx.Models.PasswordItem
import com.example.vaultx.Repository.PasswordRepository
import kotlinx.coroutines.launch

class PasswordViewModel(private val repo: PasswordRepository) : ViewModel() {

    private val _query = MutableLiveData("")
    val query: LiveData<String> = _query

    private val _selectedPassword = MutableLiveData<PasswordItem?>()
    val selectedPassword: LiveData<PasswordItem?> = _selectedPassword

    val passwords: LiveData<List<PasswordItem>> =
         _query.switchMap { query ->
            if (query.length < 2) {
                repo.passwords
            } else {
                repo.searchPasswords(query)
            }
        }

    fun setQuery(q: String) {
        _query.value = q
    }

    fun selectPassword(item: PasswordItem?) {
        _selectedPassword.value = item
    }

    fun insert(item: PasswordItem){
        viewModelScope.launch {
            repo.insert(item)
        }
    }

    fun update(item: PasswordItem){
        viewModelScope.launch {
            repo.update(item)
        }
    }

    fun delete(item: PasswordItem){
        viewModelScope.launch {
            repo.delete(item)
        }
    }
}