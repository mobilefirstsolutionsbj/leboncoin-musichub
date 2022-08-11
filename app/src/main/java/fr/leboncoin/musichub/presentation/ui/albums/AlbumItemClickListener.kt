package fr.leboncoin.musichub.presentation.ui.albums

import fr.leboncoin.musichub.domain.model.Album

interface AlbumItemClickListener {
    fun onAlbumItemClick(
        position: Int,
        item: Album
    )
}
