package com.sleepydev.bobosa.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccuracyDao {
    @Insert
    fun insertAcc(acc: Accuracy) :Long

    @Query("SELECT *  FROM Accuracy")
    fun getAllAccy(): List<Accuracy>

    @Query("DELETE FROM Accuracy")
    fun deleteAcc():Void
}