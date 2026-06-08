package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class UpdateNoteRequest(

    @SerializedName("id")
    val id : String,

    @SerializedName("img")
    val img : String,

    @SerializedName("title")
    val title : String,

    @SerializedName("description")
    val description : String,
)