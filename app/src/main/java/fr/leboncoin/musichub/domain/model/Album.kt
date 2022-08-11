package fr.leboncoin.musichub.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Album(
    val albumId: Int,
    val thumbnailUrl: String,
    val tracks: ArrayList<Track> = arrayListOf()
) : Parcelable
