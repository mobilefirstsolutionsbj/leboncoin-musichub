package fr.leboncoin.musichub.data.remote

import fr.leboncoin.musichub.common.Constants.API_PATH_TRACKS
import fr.leboncoin.musichub.domain.model.Track
import retrofit2.http.GET

interface Api {

    @GET(API_PATH_TRACKS)
    suspend fun fetchTracks(): List<Track>
}
