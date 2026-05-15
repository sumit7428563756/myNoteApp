package app.personal.mynote.model.response

import com.google.gson.annotations.SerializedName

data class SendOtpResponse(
    @SerializedName("message")
    val message: String,


    @SerializedName("otp")
    val otp: Int,

    )