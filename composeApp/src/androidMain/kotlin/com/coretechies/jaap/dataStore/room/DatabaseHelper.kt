package com.coretechies.jaap.dataStore.room

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.coretechies.jaap.room.DatabaseHelper

fun getDatabaseHelper(context: Context): DatabaseHelper {
    val dbFile = context.getDatabasePath("Jaap.db")
    return Room.databaseBuilder<DatabaseHelper>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}