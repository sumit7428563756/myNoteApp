package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.CreateNoteResponse
import app.personal.mynote.model.response.ForgotOtpResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(img : String, title : String, description : String): NetworkResult<CreateNoteResponse> {

        if (img.isBlank() || title.isBlank() || description.isBlank()) {
            return NetworkResult.Error(
                message = "Image, title, description is required"
            )
        }



        return repository.createNote(img,title,description)

    }

}