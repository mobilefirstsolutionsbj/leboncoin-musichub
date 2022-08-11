package fr.leboncoin.musichub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.leboncoin.musichub.domain.model.Track

@Database(entities = [Track::class], version = 1, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao

    companion object {
        @Volatile
        private var INSTANCE: TrackDatabase? = null

        fun getDatabase(appContext: Context): TrackDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext, TrackDatabase::class.java,
                    TrackDatabase::class.simpleName!!
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
