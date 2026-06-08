package app.personal.mynote.model.response

import app.personal.mynote.model.data.NotesData
import com.google.gson.annotations.SerializedName

class UpdateNoteResponse(

    @SerializedName("message")
    val message: String,


    @SerializedName("notes")
    val notes: NotesData,
)