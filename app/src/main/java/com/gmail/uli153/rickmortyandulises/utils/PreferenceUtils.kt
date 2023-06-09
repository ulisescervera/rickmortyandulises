package com.gmail.uli153.rickmortyandulises.utils

import android.content.Context
import android.content.SharedPreferences
import com.gmail.uli153.rickmortyandulises.domain.Formatters
import java.util.Date

class PreferenceUtils(private val context: Context) {

    private enum class PreferenceKeys(val key: String) {
        CHARACTER_REFRESH_DATE("CHARACTER_REFRESH_DATE")
    }

    private val PREFERENCE_FILE = "RMU_PREFERENCE_FILE"

    private val preferences: SharedPreferences get() {
        return context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
    }

    fun updateCharacterRefreshDate() {
        val now = Formatters.remoteDateFormatter.format(Date())
        saveString(PreferenceKeys.CHARACTER_REFRESH_DATE, now)
    }

    fun getCharacterRefreshDate(): Date? {
        val date = getString(PreferenceKeys.CHARACTER_REFRESH_DATE) ?: return null
        return try {
            Formatters.remoteDateFormatter.parse(date)
        } catch (e: Exception) {
            null
        }
    }

    private fun saveString(key: PreferenceKeys, value: String) {
        preferences.edit().putString(key.key, value).apply()
    }

    private fun getString(key: PreferenceKeys): String? {
        return preferences.getString(key.key, "")
    }
}