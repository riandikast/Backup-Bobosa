package com.sleepydev.bobosa.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert
    fun insertHistory(history: History) :Long

    @Query("SELECT *  FROM History")
    fun getAllHistory(): List<History>

    @Query("DELETE FROM History")
    fun deleteHistory():Void
}