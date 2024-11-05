package com.coretechies.jaap.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DataStoreManager(private val dataStore: DataStore<Preferences>, private val scope: CoroutineScope) {

    //Data Store Preference Keys
     val counterKey = intPreferencesKey("counter")
     val themeKey = booleanPreferencesKey("ThemeKey")
     val darkModeKey = booleanPreferencesKey("DarkMode")
     val beepSoundKey = booleanPreferencesKey("BeepSoundEnabled")
     val vibrationKey = booleanPreferencesKey("VibrationEnabled")

    // Getter for counter
    val counter: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[counterKey] ?: 0
        }

    // Setter for counter
    fun setCounter(value: Int) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[counterKey] = value
            }
        }
    }


    // Getter for manageThemeImage
    val manageThemeImage: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[themeKey] ?: false
        }

    // Setter for manageThemeImage
    fun setManageThemeImage(value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[themeKey] = value
            }
        }
    }


    // Getter for DarkMode
    val darkMode: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[darkModeKey] ?: false
        }

    // Setter for DarkMode
    fun setDarkMode(value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[darkModeKey] = value
            }
        }
    }

    // Getter for BeepSoundEnabled
    val beepSoundEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[beepSoundKey] ?: false
        }

    // Setter for BeepSoundEnabled
    fun setBeepSoundEnabled(value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[beepSoundKey] = value
            }
        }
    }

    // Getter for VibrationEnabled
    val vibrationEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[vibrationKey] ?: true
        }

    // Setter for VibrationEnabled
    fun setVibrationEnabled(value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[vibrationKey] = value
            }
        }
    }
}

