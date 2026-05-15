package app.personal.mynote.network.resource

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
) {
    class Idle<T> : NetworkResult<T>()
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, code: Int? = null) : NetworkResult<T>(null, message, code)
    class Loading<T> : NetworkResult<T>()
}