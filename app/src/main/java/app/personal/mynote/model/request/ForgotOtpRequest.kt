package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class ForgotOtpRequest(

    @SerializedName("phone")
    val phone: String
)