package com.sleepydev.bobosa.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BatchDao {
    @Insert
    fun insertBatch(batch: Batch) :Long

    @Query("SELECT *  FROM Batch")
    fun getAllBatch(): List<Batch>

    @Query("DELETE FROM Batch")
    fun deleteBatch():Void
}