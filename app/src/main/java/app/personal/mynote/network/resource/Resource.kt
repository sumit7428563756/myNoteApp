package app.personal.mynote.network.resource

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
) {
    class Idle<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, code: Int? = null) : Resource<T>(null, message, code)
    class Loading<T> : Resource<T>()
}