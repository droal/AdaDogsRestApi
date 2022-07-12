package com.example.adadogsrestapi.storage

import android.content.SharedPreferences
import com.example.adadogsrestapi.TOKEN_KEY


class SharedPreferencesStorage(private val sharedPreferences: SharedPreferences) :Storage {

    override fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, "")
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}