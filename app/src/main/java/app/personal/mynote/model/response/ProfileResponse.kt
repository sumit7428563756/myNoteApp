package app.personal.mynote.model.response

import app.personal.mynote.model.data.userData
import com.google.gson.annotations.SerializedName

class ProfileResponse(

    @SerializedName("success")
    val success : Boolean,

    @SerializedName("message")
    val message : String,

    @SerializedName("user")
    val userData: userData

)