package fr.leboncoin.musichub.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.musichub.data.local.TrackDao
import fr.leboncoin.musichub.data.local.TrackDatabase
import fr.leboncoin.musichub.data.remote.Api
import fr.leboncoin.musichub.data.repository.TracksRepositoryImpl
import fr.leboncoin.musichub.domain.repository.TracksRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTracksRepository(api: Api, trackDao: TrackDao): TracksRepository {
        return TracksRepositoryImpl(api, trackDao)
    }

    @Provides
    @Singleton
    fun provideTrackDao(@ApplicationContext appContext: Context): TrackDao {
        return TrackDatabase.getDatabase(appContext).trackDao()
    }
}
