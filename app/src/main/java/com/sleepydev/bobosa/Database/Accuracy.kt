package com.sleepydev.bobosa.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Accuracy")
@Parcelize
data class Accuracy(
    @PrimaryKey(autoGenerate = true)
    var idAcc:Int?,
    @ColumnInfo(name = "ldAcc") var lingkar_dada: String,
    @ColumnInfo(name = "pbAcc") var panjang_badan: String,
    @ColumnInfo(name = "bb") var bobot_badan: String,
    @ColumnInfo(name = "persenSch") var sch: String,
    @ColumnInfo(name = "persenSchID") var schID: String,
    @ColumnInfo(name = "persenWinter") var winter: String,
    @ColumnInfo(name = "persenWinterID") var wID: String,
    @ColumnInfo(name = "persenLambourne") var lamb: String,
    @ColumnInfo(name = "persenDjagra") var djagra: String,
    @ColumnInfo(name = "persenNN") var neural_network: String,
    @ColumnInfo(name = "timeBatch") var waktu: String,
): Parcelable

