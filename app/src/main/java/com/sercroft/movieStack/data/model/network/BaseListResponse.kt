package com.sercroft.movieStack.data.model.network

interface BaseListResponse<T> {
    var results: List<T>
}
