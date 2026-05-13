package app.personal.mynote.network.di

import app.personal.mynote.network.consts.ApiConstant
import app.personal.mynote.network.consts.NetworkConstants
import app.personal.mynote.network.interceptor.AuthInterceptor
import app.personal.mynote.network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val IS_DEBUG = true

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {

        return HttpLoggingInterceptor().apply {

            level =
                if (IS_DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()

            .addInterceptor(authInterceptor)

            .addInterceptor(loggingInterceptor)

            .connectTimeout(
                NetworkConstants.CONNECT_TIMEOUT,
                TimeUnit.SECONDS
            )

            .readTimeout(
                NetworkConstants.READ_TIMEOUT,
                TimeUnit.SECONDS
            )

            .writeTimeout(
                NetworkConstants.WRITE_TIMEOUT,
                TimeUnit.SECONDS
            )

            .retryOnConnectionFailure(true)

            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()

            .baseUrl(ApiConstant.BASE_URL)

            .client(okHttpClient)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {

        return retrofit.create(
            ApiService::class.java
        )
    }
}