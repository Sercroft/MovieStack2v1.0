package com.sercroft.movieStack.data.model.network

import com.sercroft.movieStack.data.model.entity.Cast
import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("cast")
    override var results: List<Cast>
) : BaseListResponse<Cast>


