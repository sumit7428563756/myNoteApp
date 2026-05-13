package app.personal.mynote.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: () -> String?
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val token = tokenProvider()

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