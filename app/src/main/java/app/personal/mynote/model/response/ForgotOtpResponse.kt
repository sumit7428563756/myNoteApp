package app.personal.mynote.model.response

import com.google.gson.annotations.SerializedName

class ForgotOtpResponse(

    @SerializedName("message")
    val message : String,


    @SerializedName("otp")
    val otp : String,

)