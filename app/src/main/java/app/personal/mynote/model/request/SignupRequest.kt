package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

data class SignupRequest(

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,


    @SerializedName("age")
    val age: String,

    @SerializedName("gender")
    val gender: String,


    @SerializedName("password")
    val password: String,

    @SerializedName("confirmPassword")
    val confirmPassword: String,

)