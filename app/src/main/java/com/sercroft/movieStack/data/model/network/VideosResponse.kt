package com.sercroft.movieStack.data.model.network

import com.sercroft.movieStack.data.model.entity.Video
import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("results")
    override var results: List<Video>
) : BaseListResponse<Video>
