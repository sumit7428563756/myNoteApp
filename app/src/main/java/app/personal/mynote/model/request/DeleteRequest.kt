package app.personal.mynote.model.request

import com.google.gson.annotations.SerializedName

class DeleteRequest(
    @SerializedName("id")
    val id: String
)