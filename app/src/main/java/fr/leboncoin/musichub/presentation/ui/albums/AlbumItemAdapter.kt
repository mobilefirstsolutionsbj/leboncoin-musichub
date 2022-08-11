package fr.leboncoin.musichub.presentation.ui.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.domain.model.Album

class AlbumItemAdapter(
    private val listener: AlbumItemClickListener
) :
    RecyclerView.Adapter<AlbumItemViewHolder>() {

    private val albums: ArrayList<Album> = arrayListOf()

    companion object {
        const val SPAN_SIZE: Int = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        val padding = parent.resources.getDimensionPixelSize(R.dimen.margin_tiny)
        val width = (parent.measuredWidth - padding * 2) / 2
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.width = width
        layoutParams.height = width
        parent.layoutParams = layoutParams
        return AlbumItemViewHolder(
            view
        )
    }

    fun updateDataSet(items: List<Album>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(albums, items))
        albums.clear()
        albums.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemId(position: Int): Long {
        return albums[position].albumId.toLong()
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, index: Int) {
        val album: Album = albums[holder.adapterPosition]
        val layoutParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        var marginStart = 0
        var marginEnd = 0
        var marginTop = 0
        val padding = holder.itemView.resources.getDimensionPixelSize(R.dimen.margin_tiny)
        if (index % SPAN_SIZE == 0) {
            marginEnd = padding / SPAN_SIZE
        } else {
            marginStart = padding / SPAN_SIZE
        }
        if (index >= SPAN_SIZE) {
            marginTop = padding
        }
        layoutParams.setMargins(marginStart, marginTop, marginEnd, 0)

        holder.itemView.setOnClickListener {
            listener.onAlbumItemClick(holder.adapterPosition, album)
        }

        holder.bind(album)
    }

    private class DiffUtilCallback(
        private val oldList: List<Album>,
        private val newList: List<Album>
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
