package app.personal.mynote.model.data

import com.google.gson.annotations.SerializedName

data class userData(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("gender")
    val gender: String
)