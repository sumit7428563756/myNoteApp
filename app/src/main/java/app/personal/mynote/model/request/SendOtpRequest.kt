package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

data class SendOtpRequest(

    @SerializedName("phone")
    val phone: String

)