package com.sercroft.movieStack.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.sercroft.movieStack.data.db.repository.MovieRepository
import com.sercroft.movieStack.data.model.Event
import com.sercroft.movieStack.data.model.GoToMovie
import com.sercroft.movieStack.data.model.entity.Movie
import com.sercroft.movieStack.ui.BaseViewModel
import com.sercroft.movieStack.util.extension.appendList
import com.sercroft.movieStack.util.extension.liveDataBlockScope


class MoviesViewModel : BaseViewModel(), GoToMovie {

    private val movieRepository = MovieRepository()
    private val loadedMovies: LiveData<List<Movie>>
    private val moviesPage = MutableLiveData<Int>().apply { value = 1 }

    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()

    val movieList = MediatorLiveData<MutableList<Movie>>()

    init {
        loadedMovies = moviesPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadDiscoverList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }

        movieList.addSource(loadedMovies) { it?.let { list -> movieList.appendList(list) } }
    }

    fun loadMoreMovies() {
        moviesPage.value = moviesPage.value?.plus(1)
    }
}