package com.sercroft.movieStack.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sercroft.movieStack.data.db.repository.MovieRepository
import com.sercroft.movieStack.data.model.Event
import com.sercroft.movieStack.data.model.GoToCast
import com.sercroft.movieStack.data.model.GoToVideo
import com.sercroft.movieStack.data.model.entity.Cast
import com.sercroft.movieStack.data.model.entity.Movie
import com.sercroft.movieStack.data.model.entity.Video
import com.sercroft.movieStack.ui.BaseViewModel
import com.sercroft.movieStack.util.extension.liveDataBlockScope

class MovieDetailsViewModelFactory(private val movieId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieId) as T
    }
}

class MovieDetailsViewModel(movieId: Int) : BaseViewModel(), GoToCast, GoToVideo {

    private val movieRepository = MovieRepository()
    val movie: LiveData<Movie>
    val videoList: LiveData<List<Video>>
    val castList: LiveData<List<Cast>>

    override val goToCastDetailsEvent: MutableLiveData<Event<Cast>> = MutableLiveData()
    override val goToVideoEvent: MutableLiveData<Event<Video>> = MutableLiveData()

    init {
        movie = liveDataBlockScope {
            movieRepository.loadDetails(movieId) { mSnackBarText.postValue(Event(it)) }
        }

        videoList = liveDataBlockScope {
            movieRepository.loadVideos(movieId) { mSnackBarText.postValue(Event(it)) }
        }

        castList = liveDataBlockScope {
            movieRepository.loadCredits(movieId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}
