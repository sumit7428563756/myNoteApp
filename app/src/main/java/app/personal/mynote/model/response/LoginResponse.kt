package app.personal.mynote.model.response

import app.personal.mynote.model.data.userData
import com.google.gson.annotations.SerializedName

class LoginResponse(


    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val user: userData

)