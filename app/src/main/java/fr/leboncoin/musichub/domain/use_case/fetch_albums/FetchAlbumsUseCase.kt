package fr.leboncoin.musichub.domain.use_case.fetch_albums

import android.util.Log
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.common.Resource
import fr.leboncoin.musichub.common.Utils
import fr.leboncoin.musichub.domain.model.Album
import fr.leboncoin.musichub.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FetchAlbumsUseCase @Inject constructor(
    private val repository: TracksRepository
) {
    companion object {
        val TAG: String = "[ ${FetchAlbumsUseCase::class.java.simpleName} ]"
    }

    operator fun invoke(): Flow<Resource<List<Album>>> = flow {
        try {
            emit(Resource.Loading())
            val tracks = repository.fetchTracks()
            val albums = Utils.getAlbums(list = tracks)
            repository.saveTracksToDB(tracks)
            emit(Resource.Success(albums))
        } catch (e: HttpException) {
            Log.e(TAG, "error: \n${e.localizedMessage}\n")
            emit(
                Resource.Error(R.string.error_unexpected_error_occurred)
            )
        } catch (e: IOException) {
            Log.e(TAG, "error: \n${e.localizedMessage}\n")
            emit(
                Resource.Error(R.string.error_check_internet_connection)
            )
        }
    }
}
