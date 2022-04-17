package com.sercroft.movieStack.ui.tv_show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sercroft.movieStack.data.db.repository.TvRepository
import com.sercroft.movieStack.data.model.Event
import com.sercroft.movieStack.data.model.GoToCast
import com.sercroft.movieStack.data.model.GoToVideo
import com.sercroft.movieStack.data.model.entity.Cast
import com.sercroft.movieStack.data.model.entity.TvShowDetails
import com.sercroft.movieStack.data.model.entity.Video
import com.sercroft.movieStack.ui.BaseViewModel
import com.sercroft.movieStack.util.extension.liveDataBlockScope

class TVShowDetailsViewModelFactory(private val tvShowId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TVShowDetailsViewModel(tvShowId) as T
    }
}

class TVShowDetailsViewModel(tvShowId: Int) : BaseViewModel(), GoToVideo, GoToCast {

    private val tvShowRepository = TvRepository()
    val tvShow: LiveData<TvShowDetails>
    val videoList: LiveData<List<Video>>
    val castList: LiveData<List<Cast>>

    override val goToVideoEvent: MutableLiveData<Event<Video>> = MutableLiveData()
    override val goToCastDetailsEvent: MutableLiveData<Event<Cast>> = MutableLiveData()

    init {
        tvShow = liveDataBlockScope {
            tvShowRepository.loadDetails(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }

        videoList = liveDataBlockScope {
            tvShowRepository.loadVideos(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }

        castList = liveDataBlockScope {
            tvShowRepository.loadCredits(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}