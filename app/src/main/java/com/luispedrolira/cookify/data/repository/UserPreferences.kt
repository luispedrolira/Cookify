package com.luispedrolira.cookify.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val FIRST_TIME_USER = booleanPreferencesKey("first_time_user")
    }

    val userFlow: Flow<UserData> = context.dataStore.data
        .map { preferences ->
            UserData(
                username = preferences[PreferencesKeys.USER_NAME],
                email = preferences[PreferencesKeys.USER_EMAIL],
                isLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
            )
        }

    val isFirstTimeUser: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.FIRST_TIME_USER] ?: true
        }

    suspend fun updateUserData(username: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = username
            preferences[PreferencesKeys.USER_EMAIL] = email
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
            preferences[PreferencesKeys.FIRST_TIME_USER] = false
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = false
            preferences.remove(PreferencesKeys.USER_NAME)
            preferences.remove(PreferencesKeys.USER_EMAIL)
        }
    }
}

data class UserData(
    val username: String? = null,
    val email: String? = null,
    val isLoggedIn: Boolean = false
)