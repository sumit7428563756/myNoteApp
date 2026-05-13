package app.personal.mynote.utils

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val pref : SharedPreferences
){

    fun getToken(): String? {
        return pref.getString("token", null)
    }

    fun saveToken(token: String) {
        pref.edit().putString("token", token).apply()
    }

    fun clear() {
        pref.edit().clear().apply()
    }

}