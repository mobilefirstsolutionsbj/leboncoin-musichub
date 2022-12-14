package fr.leboncoin.musichub.presentation.ui.album_tracks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.domain.model.Track

class TrackItemAdapter(
    val context: Context
) :
    RecyclerView.Adapter<TrackItemViewHolder>() {

    private val tracks: MutableList<Track> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackItemViewHolder(
            view
        )
    }

    fun updateDataSet(items: List<Track>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(tracks, items))
        tracks.clear()
        tracks.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return tracks.count()
    }

    override fun onBindViewHolder(holder: TrackItemViewHolder, index: Int) {
        val track: Track = tracks[index]
        holder.bind(track)
    }

    private class DiffUtilCallback(
        private val oldList: List<Track>,
        private val newList: List<Track>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].albumId == newList[newItemPosition].albumId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when (oldList[oldItemPosition].albumId) {
                newList[newItemPosition].albumId -> true
                else -> false
            }
        }
    }
}
