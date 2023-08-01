package com.sleepydev.bobosa.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "akurasi")
@Parcelize
data class Accuracy(
    @PrimaryKey(autoGenerate = true)
    var id_acc:Int?,
    @ColumnInfo(name = "lingkar_dada") var lingkar_dada: String,
    @ColumnInfo(name = "panjang_badan") var panjang_badan: String,
    @ColumnInfo(name = "bobot_badan") var bobot_badan: String,
    @ColumnInfo(name = "sch") var sch: String,
    @ColumnInfo(name = "sch_id") var sch_id: String,
    @ColumnInfo(name = "winter") var winter: String,
    @ColumnInfo(name = "winter_id") var winter_id: String,
    @ColumnInfo(name = "lamb") var lamb: String,
    @ColumnInfo(name = "djagra") var djagra: String,
    @ColumnInfo(name = "neural_network") var neural_network: String,
    @ColumnInfo(name = "waktu") var waktu: String,
): Parcelable

