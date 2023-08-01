package com.sleepydev.bobosa.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "estimasi_tunggal")
@Parcelize
data class Batch(
    @PrimaryKey(autoGenerate = true)
    var id_et:Int?,
    @ColumnInfo(name = "lingkar_dada") var lingkar_dada: String,
    @ColumnInfo(name = "panjang_badan") var panjang_badan: String,
    @ColumnInfo(name = "hasil") var hasil: String,
    @ColumnInfo(name = "rumus") var rumus: String,
    @ColumnInfo(name = "waktu") var waktu: String,
):Parcelable
