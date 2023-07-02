package com.example.sbi.datastore

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sbi.ui.theme.White
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(val context: Context) {

    suspend fun saveSettings(settingsData: SettingsData, paramName: String) {
        context.dataStore.edit { pref ->

            pref[intPreferencesKey("name")] = settingsData.textSize
            pref[longPreferencesKey("bg_color")] = settingsData.bgColor
            pref[booleanPreferencesKey(paramName)] = settingsData.buttonState
        }
    }

    fun getSettings(paramName: String) = context.dataStore.data.map { pref ->
        return@map SettingsData(
            pref[intPreferencesKey("text_size")] ?: 40,
            pref[longPreferencesKey("bg_color")] ?: White.value.toLong(),
            pref[booleanPreferencesKey(paramName)] ?: false
        )
    }
}


class objectDataStore(context: Context, storageName: String) {

    private val prefs: SharedPreferences = context.getSharedPreferences(storageName,
        Context.MODE_PRIVATE
    )
    private val prefsEditor: SharedPreferences.Editor = prefs.edit()

    fun setObjectState(objectName: String, state: Boolean) {
        prefsEditor.clear()
        prefsEditor.putBoolean(objectName, state)
        prefsEditor.commit()
    }

    fun getObjectState(objectName: String): Boolean = prefs.getBoolean(objectName, false)


}


