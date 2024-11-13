package com.coretechies.jaap.room


import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.coretechies.jaap.room.counter.CountingDao
import com.coretechies.jaap.room.counter.CountingDetails


@Database(entities = [CountingDetails::class], version = 2)
@ConstructedBy(DatabaseHelperConstructor::class)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun countingDao(): CountingDao

}