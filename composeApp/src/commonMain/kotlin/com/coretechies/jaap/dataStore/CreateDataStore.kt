package com.coretechies.jaap.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

object DataStoreSingleton {
    private var dataStore: DataStore<Preferences>? = null

    fun getInstance(producePath: () -> String): DataStore<Preferences> {
        if (dataStore == null) {
            dataStore = PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            )
        }
        return dataStore!!
    }
}

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"
