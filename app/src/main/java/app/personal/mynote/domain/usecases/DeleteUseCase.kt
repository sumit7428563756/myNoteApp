package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.DeleteResponse
import app.personal.mynote.model.response.NotesResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class DeleteUseCase    @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(id : String): NetworkResult<DeleteResponse> {
        return repository.deleteNote(id)
    }
}