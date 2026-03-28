package com.example.vaultx.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultx.Models.PasswordItem
import com.example.vaultx.Repository.PasswordRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PasswordViewModel(private val repo: PasswordRepository) : ViewModel() {

    val passwords = repo.passwords

    private val selectPasswords = MutableLiveData<PasswordItem?>()
    val selectpassword : LiveData<PasswordItem?> = selectPasswords

    private val _searchResult = MutableLiveData<List<PasswordItem>>()
    val searchResult: LiveData<List<PasswordItem>> = _searchResult

    private var searchJob: Job? = null

    fun selectPasswords(item: PasswordItem?){
        selectPasswords.value = item
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

    fun searchPasswords(query:String){

        if(query.isEmpty() || query.length < 2){
            searchJob?.cancel()
            _searchResult.value = emptyList()
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repo.searchPasswords(query).collect {
                _searchResult.value = it
            }
        }
    }

}