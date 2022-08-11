package fr.leboncoin.musichub.domain.repository

import fr.leboncoin.musichub.domain.model.Track

interface TracksRepository {
    suspend fun fetchTracks(): List<Track>
    suspend fun fetchLocalTracks(): List<Track>
    suspend fun saveTracksToDB(list: List<Track>)
}
