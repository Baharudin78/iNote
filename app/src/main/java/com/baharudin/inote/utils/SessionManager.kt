package com.baharudin.inote.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.baharudin.inote.utils.Constant.EMAIL_KEY
import com.baharudin.inote.utils.Constant.JWT_TOKEN_KEY
import com.baharudin.inote.utils.Constant.NAME_KEY
import kotlinx.coroutines.flow.first

class SessionManager(val context : Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun updateSession(token : String, nama : String, email : String) {
        val jwtToken = stringPreferencesKey(JWT_TOKEN_KEY)
        val nameKey = stringPreferencesKey(NAME_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        context.datastore.edit { preferences ->
            preferences[jwtToken] = token
            preferences[nameKey] = nama
            preferences[emailKey] = email
        }
    }
    suspend fun getJwtToken() : String? {
        val jwtToken = stringPreferencesKey(JWT_TOKEN_KEY)
        val preferences = context.datastore.data.first()
        return preferences[jwtToken]
    }

    suspend fun getCurrentUserName() : String? {
        val namaKey = stringPreferencesKey(NAME_KEY)
        val preferences = context.datastore.data.first()
        return preferences[namaKey]
    }

    suspend fun getCurrentEmail() : String? {
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        val preferences = context.datastore.data.first()
        return preferences[emailKey]
    }
    suspend fun logOut() {
        context.datastore.edit {
            it.clear()
        }
    }
}