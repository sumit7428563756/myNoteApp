package app.personal.mynote.domain.usecases

import app.personal.mynote.model.response.NotesResponse
import app.personal.mynote.model.response.ProfileResponse
import app.personal.mynote.network.repository.AuthRepository
import app.personal.mynote.network.resource.NetworkResult
import javax.inject.Inject

class NoteUseCase    @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): NetworkResult<NotesResponse> {
        return repository.getNotes()
    }
}