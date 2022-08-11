package fr.leboncoin.musichub.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.musichub.data.remote.Api
import fr.leboncoin.musichub.data.repository.TracksRepositoryImpl
import fr.leboncoin.musichub.domain.repository.TracksRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTracksRepository(api: Api): TracksRepository {
        return TracksRepositoryImpl(api)
    }
}
