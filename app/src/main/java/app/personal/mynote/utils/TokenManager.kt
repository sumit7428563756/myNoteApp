package app.personal.mynote.utils

import android.content.SharedPreferences
import app.personal.mynote.model.data.userData
import com.google.gson.Gson
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



    fun saveUser(user: userData) {
        val json = Gson().toJson(user)
        pref.edit().putString("user", json).apply()
    }


    fun getUser(): userData? {
        val json = pref.getString("user", null)
        return if (json != null) {
            Gson().fromJson(json, userData::class.java)
        } else null
    }

}