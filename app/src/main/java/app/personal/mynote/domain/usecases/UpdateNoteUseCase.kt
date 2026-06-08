package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.CreateNoteResponse
import app.personal.mynote.model.response.UpdateNoteResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(id : String, img : String, title : String, description : String): NetworkResult<UpdateNoteResponse> {

        if (id.isBlank() || img.isBlank() || title.isBlank() || description.isBlank()) {
            return NetworkResult.Error(
                message = "Id, Image, title, description is required"
            )
        }



        return repository.updateNote(id, img,title,description)

    }

}