package com.coretechies.jaap.room.counter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CountingDetails")
data class CountingDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val totalCount: Int,
    val countTitle: String,
    val countDate: String,
    val countingDetailsUserId: String,
    val countingDetailsUserName: String
)