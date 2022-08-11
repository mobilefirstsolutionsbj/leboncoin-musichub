package fr.leboncoin.musichub.presentation.ui.album_tracks

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.domain.model.Track

class TrackItemViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private var title: TextView = view.findViewById(R.id.track_title)
    private var thumbnail: ImageView = view.findViewById(R.id.track_thumbnail)

    fun bind(track: Track) {
        title.text = track.title
        thumbnail.setImageDrawable(null)

        val width = 150

        Picasso.get()
            .load(track.thumbnailUrl)
            .resize(width, width)
            .placeholder(R.drawable.thumbnail_placeholder)
            .error(R.drawable.thumbnail_placeholder)
            .stableKey(track.id.toString())
            .into(thumbnail)
    }
}
