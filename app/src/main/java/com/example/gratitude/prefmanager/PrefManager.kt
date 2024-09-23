package com.example.gratitude.prefmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrefManager(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    fun setUserName(key: String, id: String) {
        pref?.edit { putString(key, id) }
    }

    fun getUserName(key: String): String {
        return pref?.getString(key, "") ?: ""
    }

    fun setIsLoginComplete(key: String, value: Boolean) {
        pref?.edit { putBoolean(key, value) }
    }

    fun getIsLoginComplete(key: String): Boolean {
        return pref?.getBoolean(key, false)!!
    }

}