package com.example.gravitytesttask.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.gravitytesttask.Constants
import com.example.gravitytesttask.data.remote.Api
import com.example.gravitytesttask.data.remote.ApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    @ApplicationContext private val appContext: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.API_RESPONSE_DATASTORE)
    private val linkPreferenceKey = stringPreferencesKey(Constants.LINK_PREFERENCE_KEY)
    private val homePreferencesKey = stringPreferencesKey(Constants.HOME_PREFERENCE_KEY)

    suspend fun getLinkFromApi(): String {
        val response = api.getResponse()
        saveResponse(response)
        return response.link
    }

    suspend fun getHome(): String? = appContext.dataStore.data
        .map { preferences ->
            preferences[homePreferencesKey]
        }.first()

    private suspend fun saveResponse(response: ApiResponse) {
        appContext.dataStore.edit { preferences ->
            preferences[linkPreferenceKey] = response.link
            preferences[homePreferencesKey] = response.home
        }
    }

}