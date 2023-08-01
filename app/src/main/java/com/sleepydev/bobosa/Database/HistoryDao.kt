package com.sleepydev.bobosa.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert
    fun insertHistory(history: History) :Long

    @Query("SELECT *  FROM riwayat")
    fun getAllHistory(): List<History>

    @Query("DELETE FROM riwayat")
    fun deleteHistory():Void

    @Insert
    fun insertBackupTime(backup: BackupTime) :Long
    @Query("DELETE FROM waktu_backup")
    fun deleteBackupTime() :Int

    @Query("SELECT *  FROM waktu_backup")
    fun getBackupTime():BackupTime
}