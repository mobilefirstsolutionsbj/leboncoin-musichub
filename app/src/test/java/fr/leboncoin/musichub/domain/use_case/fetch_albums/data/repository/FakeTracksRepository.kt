package fr.leboncoin.musichub.domain.use_case.fetch_albums.data.repository

import fr.leboncoin.musichub.domain.model.Track
import fr.leboncoin.musichub.domain.repository.TracksRepository

class FakeTracksRepository : TracksRepository {

    companion object {

        private const val url = "https://via.placeholder.com/600/d32776"
        private const val thumbnailUrl = "https://via.placeholder.com/150/d32776"

        private val track1 = Track(1, 1, "Title #1", url, thumbnailUrl)
        private val track2 = Track(1, 2, "Title #2", url, thumbnailUrl)
        private val track3 = Track(1, 3, "Title #3", url, thumbnailUrl)
        private val track4 = Track(1, 4, "Title #4", url, thumbnailUrl)

        private val track5 = Track(2, 5, "Title #5", url, thumbnailUrl)
        private val track6 = Track(2, 6, "Title #6", url, thumbnailUrl)
        private val track7 = Track(2, 7, "Title #7", url, thumbnailUrl)
        private val track8 = Track(2, 8, "Title #8", url, thumbnailUrl)

        private val track9 = Track(3, 9, "Title #9", url, thumbnailUrl)
        private val track10 = Track(3, 10, "Title #10", url, thumbnailUrl)
        private val track11 = Track(3, 11, "Title #11", url, thumbnailUrl)
        private val track12 = Track(3, 12, "Title #12", url, thumbnailUrl)

        private val track13 = Track(4, 13, "Title #13", url, thumbnailUrl)
        private val track14 = Track(4, 14, "Title #14", url, thumbnailUrl)
        private val track15 = Track(4, 15, "Title #15", url, thumbnailUrl)
        private val track16 = Track(4, 16, "Title #16", url, thumbnailUrl)

        val FAKE_TRACKS = arrayListOf(
            track1, track2, track3, track4,
            track5, track6, track7, track8,
            track9, track10, track11, track12,
            track13, track14, track15, track16
        )
    }

    override suspend fun fetchTracks() = FAKE_TRACKS
    override suspend fun fetchLocalTracks() = FAKE_TRACKS

    override suspend fun saveTracksToDB(list: List<Track>) { }
}
