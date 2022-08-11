package fr.leboncoin.musichub.data.repository

import fr.leboncoin.musichub.data.local.TrackDao
import fr.leboncoin.musichub.data.remote.Api
import fr.leboncoin.musichub.domain.model.Track
import fr.leboncoin.musichub.domain.repository.TracksRepository
import javax.inject.Inject

class TracksRepositoryImpl @Inject constructor(
    private val api: Api,
    private val trackDao: TrackDao
) : TracksRepository {
    override suspend fun fetchTracks(): List<Track> {
        return api.fetchTracks()
    }

    override suspend fun fetchLocalTracks(): List<Track> {
        return trackDao.getSortedTracks()
    }

    override suspend fun saveTracksToDB(list: List<Track>) {
        list.forEach { track ->
            trackDao.saveTrack(track)
        }
    }
}
