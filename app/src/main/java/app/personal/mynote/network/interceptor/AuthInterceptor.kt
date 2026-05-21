package app.personal.mynote.network.interceptor

import app.personal.mynote.network.service.AuthRequired
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

        val originalRequest = chain.request()

        val invocation =
            originalRequest.tag(retrofit2.Invocation::class.java)

        val requiresAuth =
            invocation?.method()?.isAnnotationPresent(
                AuthRequired::class.java
            ) ?: false

        val requestBuilder =
            originalRequest.newBuilder()

        if (requiresAuth) {

            tokenManager.getToken()?.let { token ->

                requestBuilder.addHeader(
                    "Authorization",
                    "Bearer $token"
                )
            }
        }

        requestBuilder.addHeader(
            "Accept",
            "application/json"
        )

        return chain.proceed(
            requestBuilder.build()
        )

    }
}