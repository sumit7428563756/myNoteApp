package app.personal.mynote.model.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String
)
