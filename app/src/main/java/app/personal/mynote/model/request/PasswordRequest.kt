package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class PasswordRequest(
    @SerializedName("phone")
    val phone: String,

    @SerializedName("otp")
    val otp : String,

    @SerializedName("newPassword")
    val newPassword : String
)