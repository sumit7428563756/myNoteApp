package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("phone")
    val phone : String,

    @SerializedName("otp")
    val otp : String
)