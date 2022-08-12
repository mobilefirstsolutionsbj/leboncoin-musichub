package fr.leboncoin.musichub.domain.use_case.fetch_albums

import com.google.common.truth.Truth
import fr.leboncoin.musichub.common.Utils
import fr.leboncoin.musichub.domain.use_case.fetch_albums.data.repository.FakeTracksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchTracksUseCaseTest {

    private lateinit var useCase: FetchAlbumsUseCase
    private lateinit var repository: FakeTracksRepository

    @Before
    fun setup() {
        repository = FakeTracksRepository()
        useCase = FetchAlbumsUseCase(repository)
    }

    @Test
    fun `Fetch Tracks and expect successful result`() {
        runBlocking {
            launch {
                useCase.invoke().last().also { list ->
                    val albums = list.data ?: arrayListOf()
                    Truth.assertThat(albums.count())
                        .isEqualTo((Utils.getAlbums(FakeTracksRepository.FAKE_TRACKS).count()))
                }
            }
        }
    }
}
