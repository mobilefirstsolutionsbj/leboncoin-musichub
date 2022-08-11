package fr.leboncoin.musichub.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Track(
    @field:SerializedName("albumId")
    val albumId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("thumbnailUrl")
    val thumbnailUrl: String
) : Parcelable
