package com.example.datastoreexample

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
private val Context._dataStore by preferencesDataStore("app_preferences")
class UserManager(context: Context) {
    
    private val dataStore : DataStore<Preferences> = context._dataStore
    companion object{
        val USER_AGE_KEY = intPreferencesKey("age")
        val USER_NAME_KEY = stringPreferencesKey("name")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys

    suspend fun storeUser(name:String,age:Int){
     dataStore.edit {
         it[USER_NAME_KEY]=name
         it[USER_AGE_KEY]=age
     }
    }

    // Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine
    val userAgeFlow: Flow<Int> = dataStore.data.map {
        it[USER_AGE_KEY] ?: 0
    }

    val userNameFlow :Flow<String> = dataStore.data.map{
        it[USER_NAME_KEY]?:""
    }
}