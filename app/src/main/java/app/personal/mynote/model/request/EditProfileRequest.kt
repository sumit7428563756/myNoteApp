package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class EditProfileRequest(

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
)