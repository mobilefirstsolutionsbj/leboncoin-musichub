package fr.leboncoin.musichub.domain.repository

import fr.leboncoin.musichub.domain.model.Track

interface TracksRepository {
    suspend fun fetchTracks(): List<Track>
}
