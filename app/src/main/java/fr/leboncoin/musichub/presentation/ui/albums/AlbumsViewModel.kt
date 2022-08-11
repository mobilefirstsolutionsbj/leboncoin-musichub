package fr.leboncoin.musichub.presentation.ui.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.musichub.common.Resource
import fr.leboncoin.musichub.domain.model.Album
import fr.leboncoin.musichub.domain.use_case.fetch_albums.FetchAlbumsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
/**
 * View model responsible for preparing and managing the data for [AlbumsViewModel].
 *
 * @see AlbumsViewModel
 */
class AlbumsViewModel @Inject constructor(
    private val fetchAlbumsUseCase: FetchAlbumsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<FetchItemListState<Album>>()
    val state: LiveData<FetchItemListState<Album>> = _state

    fun fetchAlbums() {
        fetchAlbumsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FetchItemListState(items = result.data)
                }

                is Resource.Error -> {
                    _state.value = FetchItemListState(error = result.errorResId)
                }

                is Resource.Loading -> {
                    _state.value = FetchItemListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
