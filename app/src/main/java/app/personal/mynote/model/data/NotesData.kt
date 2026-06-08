package app.personal.mynote.model.data

import com.google.gson.annotations.SerializedName

data class NotesData(

    @SerializedName("id")
    val id: Int,

    @SerializedName("img")
    val img: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("date")
    val date: String,

)