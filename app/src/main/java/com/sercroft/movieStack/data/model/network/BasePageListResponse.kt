package com.sercroft.movieStack.data.model.network

interface BasePageListResponse<T> {
    var page: Int
    var results: List<T>
}