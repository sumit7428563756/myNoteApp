package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class CreateNoteRequest(

    @SerializedName("img")
    val img: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("email")
    val description: String,
)