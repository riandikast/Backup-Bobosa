package com.sleepydev.bobosa.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Batch")
@Parcelize
data class Batch(
    @PrimaryKey(autoGenerate = true)
    var idBatch:Int?,
    @ColumnInfo(name = "ldBatch") var lingkar_dada: String,
    @ColumnInfo(name = "pbBatch") var panjang_badan: String,
    @ColumnInfo(name = "hasil") var hasil: String,
    @ColumnInfo(name = "rumus") var rumus: String,
    @ColumnInfo(name = "timeBatch") var waktu: String,
):Parcelable
