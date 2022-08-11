package fr.leboncoin.musichub.common

import androidx.annotation.StringRes

sealed class Resource<T>(val data: T? = null, val errorResId: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(@StringRes errorResId: Int, data: T? = null) : Resource<T>(data, errorResId)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
