package com.sercroft.movieStack.data.db.repository

import androidx.lifecycle.MutableLiveData
import com.sercroft.movieStack.data.model.network.BaseListResponse
import com.sercroft.movieStack.data.model.network.BasePageListResponse
import com.sercroft.movieStack.util.extension.onException
import com.sercroft.movieStack.util.extension.onFailure
import com.sercroft.movieStack.util.extension.onSuccess
import com.sercroft.movieStack.util.extension.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

abstract class BaseRepository {

    protected suspend fun <Type> loadCall(
        call: () -> Call<Type>,
        result: MutableLiveData<Type>,
        errorText: (String) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            call().request { response ->
                response.onSuccess { data?.let { result.postValue(it) } }
                response.onException { message?.let { errorText(it) } }
                response.onFailure { message?.let { errorText(it) } }
            }
            result.apply { postValue(null) }
        }

    protected suspend fun <Response : BaseListResponse<ListType>, ListType> loadListCall(
        call: () -> Call<Response>,
        result: MutableLiveData<List<ListType>>,
        errorText: (String) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            call().request { response ->
                response.onSuccess { data?.let { result.postValue((it).results) } }
                response.onException { message?.let { errorText(it) } }
                response.onFailure { message?.let { errorText(it) } }
            }
            result.apply { postValue(null) }
        }

    protected suspend fun <Response : BasePageListResponse<ListType>, ListType> loadPageListCall(
        call: () -> Call<Response>,
        result: MutableLiveData<List<ListType>>,
        errorText: (String) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            call().request { response ->
                response.onSuccess { data?.let { result.postValue((it).results) } }
                response.onException { message?.let { errorText(it) } }
                response.onFailure { message?.let { errorText(it) } }
            }
            result.apply { postValue(null) }
        }
}