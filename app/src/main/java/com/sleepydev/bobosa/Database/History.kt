package com.sleepydev.bobosa.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "riwayat")
@Parcelize
data class History(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    @ColumnInfo(name = "lingkar_dada") var lingkar_dada: String,
    @ColumnInfo(name = "panjang_badan") var panjang_badan: String,
    @ColumnInfo(name = "jenis_sapi") var jenis_sapi: String,
    @ColumnInfo(name = "schoorl_denmark") var schoorl_denmark: String,
    @ColumnInfo(name = "schoorl_ind") var schoorl_ind: String,
    @ColumnInfo(name = "winter_eropa") var winter_eropa: String,
    @ColumnInfo(name = "winter_indonesia") var winter_indonesia: String,
    @ColumnInfo(name = "lamb") var lamb: String,
    @ColumnInfo(name = "djagra") var djagra: String,
    @ColumnInfo(name = "neural_network") var neural_network: String,
    @ColumnInfo(name = "waktu") var waktu: String,
    @ColumnInfo(name = "rec") var rec: String,
): Parcelable
