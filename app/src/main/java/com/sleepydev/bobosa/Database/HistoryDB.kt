package com.sleepydev.bobosa.Database





import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [History::class, Batch::class, Accuracy::class, BackupTime::class], version = 7)
abstract class HistoryDB: RoomDatabase() {
    abstract  fun HistoryDao(): HistoryDao
    abstract  fun BatchDao(): BatchDao
    abstract  fun AccuracyDao(): AccuracyDao

    companion object{
        private var INSTANCE : HistoryDB? = null
        fun getInstance(context: Context): HistoryDB?{
            if (INSTANCE == null){
                synchronized(HistoryDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HistoryDB::class.java,"History.db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}