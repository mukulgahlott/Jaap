package com.coretechies.jaap.room.counter

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CountingDao {

    @Insert
    suspend fun insert(countingDetails: CountingDetails)

    @Delete
    suspend fun delete(countingDetails: CountingDetails)

    @Upsert
    suspend fun updateById(countingDetails: CountingDetails)

    @Query("SELECT * FROM CountingDetails")
    fun getAllCountingDetails(): Flow<List<CountingDetails>>
}