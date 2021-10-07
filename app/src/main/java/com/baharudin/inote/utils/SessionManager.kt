package com.baharudin.inote.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.baharudin.inote.utils.Constant.JWT_TOKEN_KEY
import kotlinx.coroutines.flow.first

class SessionManager(val context : Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun saveJwtToken(token : String) {
        val jwtToken = stringPreferencesKey(JWT_TOKEN_KEY)
        context.datastore.edit { preferences ->
            preferences[jwtToken] = token
        }
    }
    suspend fun getJwtToken() : String? {
        val jwtToken = stringPreferencesKey(JWT_TOKEN_KEY)
        val preferences = context.datastore.data.first()
        return preferences[jwtToken]
    }
}