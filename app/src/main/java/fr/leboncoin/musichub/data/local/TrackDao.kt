package fr.leboncoin.musichub.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.leboncoin.musichub.domain.model.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrack(track: Track)

    @Query("select * from tracks_table order by albumId asc")
    fun getSortedTracks(): List<Track>
}
