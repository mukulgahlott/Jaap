package com.coretechies.jaap.room

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DatabaseHelperConstructor : RoomDatabaseConstructor<DatabaseHelper> {
    override fun initialize(): DatabaseHelper
}