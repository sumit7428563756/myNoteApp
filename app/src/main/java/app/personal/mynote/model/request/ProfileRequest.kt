package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class ProfileRequest(
    @SerializedName("id")
    val id: String,
)