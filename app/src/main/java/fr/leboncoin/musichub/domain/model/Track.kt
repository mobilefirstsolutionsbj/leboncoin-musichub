package fr.leboncoin.musichub.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
@Entity(tableName = "tracks_table")
data class Track(
    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("albumId")
    val albumId: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("thumbnailUrl")
    val thumbnailUrl: String
) : Parcelable
