package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class LoginRequest(

    @SerializedName("phone")
    val phone: String,

    @SerializedName("password")
    val password: String
)