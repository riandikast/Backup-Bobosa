package com.sleepydev.bobosa.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "waktu_backup")
@Parcelize
data class BackupTime(
    @PrimaryKey
    var time:String,
): Parcelable


