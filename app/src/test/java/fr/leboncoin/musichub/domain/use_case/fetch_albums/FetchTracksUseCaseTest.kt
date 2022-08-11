package fr.leboncoin.musichub.domain.use_case.fetch_albums

import com.google.common.truth.Truth
import fr.leboncoin.musichub.domain.use_case.fetch_albums.data.repository.FakeTracksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
                useCase.invoke().first().also { list ->
                    Truth.assertThat(list.data?.first())
                        .isEqualTo((FakeTracksRepository.FAKE_TRACKS.first()))
                    Truth.assertThat(list.data?.last())
                        .isEqualTo((FakeTracksRepository.FAKE_TRACKS.last()))
                    Truth.assertThat(list.data?.count())
                        .isEqualTo((FakeTracksRepository.FAKE_TRACKS.count()))
                }
            }
        }
    }
}
