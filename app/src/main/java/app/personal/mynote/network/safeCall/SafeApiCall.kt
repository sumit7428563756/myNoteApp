package app.personal.mynote.network.safeCall

import app.personal.mynote.network.resource.NetworkResult
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafeApiCall @Inject constructor() {

    suspend fun <T> execute(
       apiCall: suspend () -> Response<T>
    ): NetworkResult<T> {

        return try {

            val response = apiCall()

            if (response.isSuccessful) {

                response.body()?.let {

                    NetworkResult.Success(it)

                } ?: NetworkResult.Error(
                    message = "Response body is null",
                    code = response.code()
                )

            } else {

                val errorMessage = parseError(
                    response.errorBody()
                )

                NetworkResult.Error(
                    message = errorMessage,
                    code = response.code()
                )
            }

        } catch (e: HttpException) {

            NetworkResult.Error(
                message = "HTTP ${e.code()} : ${e.message()}",
                code = e.code()
            )

        } catch (e: SocketTimeoutException) {

            NetworkResult.Error(
                message = "Request timed out. Please try again."
            )

        } catch (e: ConnectException) {

            NetworkResult.Error(
                message = "Failed to connect to server."
            )

        } catch (e: UnknownHostException) {

            NetworkResult.Error(
                message = "No internet connection."
            )

        } catch (e: IOException) {

            NetworkResult.Error(
                message = e.localizedMessage
                    ?: "Network error occurred"
            )

        } catch (e: Exception) {

            NetworkResult.Error(
                message = e.localizedMessage
                    ?: "Unexpected error occurred"
            )
        }
    }

    private fun parseError(
        errorBody: ResponseBody?
    ): String {

        return try {

            val json = JSONObject(
                errorBody?.string() ?: ""
            )

            json.optString(
                "message",
                "Unknown server error"
            )

        } catch (e: Exception) {

            "Something went wrong"
        }
    }
}