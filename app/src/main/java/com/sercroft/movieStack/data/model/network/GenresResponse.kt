package com.sercroft.movieStack.data.model.network

import com.sercroft.movieStack.data.model.entity.Genre
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    override var results: List<Genre>
) : BaseListResponse<Genre>
