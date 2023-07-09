package com.sleepydev.bobosa.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "History")
@Parcelize
data class History(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    @ColumnInfo(name = "ld") var lingkar_dada: String,
    @ColumnInfo(name = "pb") var panjang_badan: String,
    @ColumnInfo(name = "jenis") var jenis_sapi: String,
    @ColumnInfo(name = "sd") var schoorl_denmark: String,
    @ColumnInfo(name = "si") var schoorl_ind: String,
    @ColumnInfo(name = "we") var winter_eropa: String,
    @ColumnInfo(name = "wi") var winter_indonesia: String,
    @ColumnInfo(name = "ar") var arjo: String,
    @ColumnInfo(name = "time") var waktu: String,
): Parcelable
