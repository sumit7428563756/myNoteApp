package app.personal.mynote.model.response

import android.adservices.ondevicepersonalization.UserData
import com.google.gson.annotations.SerializedName

data class SignUpResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("user")
    val user: UserData
)