package fr.leboncoin.musichub.common

import fr.leboncoin.musichub.domain.model.Album
import fr.leboncoin.musichub.domain.model.Track

object Utils {

    fun getAlbums(list: List<Track>): List<Album> {
        if (list.isEmpty()) {
            return arrayListOf()
        }
        val tracks = list.sortedBy {
            it.albumId
        }
        var previousId = tracks.first().albumId
        var currentId: Int
        val albums = arrayListOf<Album>()
        tracks.forEachIndexed { _, track ->
            currentId = track.albumId
            if (currentId == previousId) {
                val currentAlbum: Album? = albums.firstOrNull {
                    it.albumId == currentId
                }
                currentAlbum?.tracks?.let {
                    if (!albums.last().tracks.contains(track)) {
                        albums.last().tracks.add(track)
                    }
                } ?: kotlin.run {
                    val albumTracks = arrayListOf<Track>()
                    val album = Album(
                        albumId = track.albumId,
                        thumbnailUrl = track.thumbnailUrl,
                        albumTracks
                    )
                    album.tracks.add(track)
                    albums.add(album)
                }
            } else {
                previousId = currentId
            }
        }
        return albums
    }
}