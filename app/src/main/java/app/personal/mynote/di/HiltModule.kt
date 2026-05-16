package app.personal.mynote.di

import app.personal.mynote.domain.repositoryImpl.AuthRepositoryImp
import app.personal.mynote.network.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImp
    ): AuthRepository





}