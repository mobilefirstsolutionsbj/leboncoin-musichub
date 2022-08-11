package fr.leboncoin.musichub.presentation.ui.albums

import androidx.annotation.StringRes

data class FetchItemListState<T>(
    val isLoading: Boolean = false,
    val items: List<T>? = null,
    @StringRes
    val error: Int? = null
)
