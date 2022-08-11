package fr.leboncoin.musichub.presentation.ui.albums

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.domain.model.Album

class AlbumItemViewHolder(
    private var view: View
) : RecyclerView.ViewHolder(view) {

    private var thumbnail: ImageView = view.findViewById(R.id.album_image)
    private var title: TextView = view.findViewById(R.id.album_title)

    fun bind(album: Album) {
        title.text = view.context?.getString(R.string.album_item_title, album.albumId.toString())
        thumbnail.setImageDrawable(null)

        val width = 150

        Picasso.get()
            .load(album.thumbnailUrl)
            .resize(width, width)
            .placeholder(R.drawable.thumbnail_placeholder)
            .error(R.drawable.thumbnail_placeholder)
            .stableKey(album.albumId.toString())
            .into(thumbnail)
    }
}
