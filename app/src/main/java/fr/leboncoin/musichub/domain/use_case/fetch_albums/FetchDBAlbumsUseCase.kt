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

class FetchDBAlbumsUseCase @Inject constructor(
    private val repository: TracksRepository
) {
    companion object {
        val TAG: String = "[ ${FetchDBAlbumsUseCase::class.java.simpleName} ]"
    }

    operator fun invoke(): Flow<Resource<List<Album>>> = flow {
        try {
            emit(Resource.Loading())
            val albums = Utils.getAlbums(list = repository.fetchLocalTracks())
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
