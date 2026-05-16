package app.personal.mynote.network.interceptor

import app.personal.mynote.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val token = tokenManager.getToken()

        val request = chain.request()
            .newBuilder()
            .apply {

                token?.let {
                    addHeader(
                        "Authorization",
                        "Bearer $it"
                    )
                }

                addHeader(
                    "Accept",
                    "application/json"
                )
            }
            .build()

        return chain.proceed(request)
    }
}