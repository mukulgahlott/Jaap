package com.coretechies.jaap.room.counter

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CountingDao {

    @Insert
    suspend fun insert(countingDetails: CountingDetails)

    @Delete
    suspend fun delete(countingDetails: CountingDetails)

    @Query("UPDATE CountingDetails SET countTitle = :countTitle, totalCount = :totalCount , countDate = :countDate WHERE id = :id")
    suspend fun updateById(id: Int, totalCount: Int, countTitle : String, countDate : String)

    @Query("SELECT * FROM CountingDetails")
    fun getAllCountingDetails(): Flow<List<CountingDetails>>
}