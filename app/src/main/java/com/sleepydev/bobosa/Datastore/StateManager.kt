package com.sleepydev.bobosa.Datastore


import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.map

class StateManager (context : Context){
    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs")
    companion object{
        val FOLDER = preferencesKey<Boolean>("Dark_State")
    }

    suspend fun saveTemporaryState(state: Boolean) {
        dataStore.edit {
            it[FOLDER] = state
        }
    }

    val tempState : kotlinx.coroutines.flow.Flow<Boolean> = dataStore.data.map {
        it [FOLDER] ?: false
    }
}