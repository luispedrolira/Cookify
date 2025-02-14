package com.luispedrolira.cookify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luispedrolira.cookify.data.repository.UserData
import com.luispedrolira.cookify.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val userData = userPreferencesRepository.userFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, UserData())

    val isFirstTimeUser = userPreferencesRepository.isFirstTimeUser
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    fun login(username: String, email: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateUserData(username, email)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.clearUserData()
        }
    }
}