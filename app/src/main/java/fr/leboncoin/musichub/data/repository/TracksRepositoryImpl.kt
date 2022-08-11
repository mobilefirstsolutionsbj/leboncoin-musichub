package fr.leboncoin.musichub.data.repository

import fr.leboncoin.musichub.data.remote.Api
import fr.leboncoin.musichub.domain.model.Track
import fr.leboncoin.musichub.domain.repository.TracksRepository
import javax.inject.Inject

class TracksRepositoryImpl @Inject constructor(
    private val api: Api
) : TracksRepository {
    override suspend fun fetchTracks(): List<Track> {
        return api.fetchTracks()
    }
}
