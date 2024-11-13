package com.coretechies.jaap.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope
) {

    //Data Store Preference Keys
    private  val idKey = intPreferencesKey("IdKey")
    private val counterKey = intPreferencesKey("Counter")
    private val malaKey = intPreferencesKey("MalaKey")
    private val titleKey = stringPreferencesKey("Title")
    private val targetKey = intPreferencesKey("Target")
    private val themeKey = booleanPreferencesKey("ThemeKey")
    private val darkModeKey = booleanPreferencesKey("DarkMode")
    private val beepSoundKey = booleanPreferencesKey("BeepSoundEnabled")
    private val vibrationKey = booleanPreferencesKey("VibrationEnabled")


    // Getter for id
    val id: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[malaKey] ?: 0
        }

    // Setter for Id
    fun setId(value: Int) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[malaKey] = value
            }
        }
    }

    // Getter for mala
    val mala: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[malaKey] ?: 0
        }

    // Setter for mala
    fun setMala(value: Int) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[malaKey] = value
            }
        }
    }

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

    // Getter for counter
    val title: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[titleKey] ?: "Digital Jaap"
        }

    // Setter for counter
    fun setTitle(value: String) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[titleKey] = value
            }
        }
    }

    // Getter for target
    val target: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[targetKey] ?: 108
        }

    // Setter for target
    fun setTarget(value: String) {
        val intValue = if (value.isNotBlank()) value.toInt() else 108
        scope.launch {
            dataStore.edit { preferences ->
                preferences[targetKey] = intValue
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

