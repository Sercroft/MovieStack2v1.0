package com.sercroft.movieStack.ui.tv_shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.sercroft.movieStack.data.db.repository.TvRepository
import com.sercroft.movieStack.data.model.Event
import com.sercroft.movieStack.data.model.GoToTvShow
import com.sercroft.movieStack.data.model.entity.TvShow
import com.sercroft.movieStack.ui.BaseViewModel
import com.sercroft.movieStack.util.extension.appendList
import com.sercroft.movieStack.util.extension.liveDataBlockScope

class TvShowsViewModel : BaseViewModel(), GoToTvShow {

    private val tvShowRepository = TvRepository()
    private val loadedTvShowList: LiveData<List<TvShow>>
    private val tvShowsPage = MutableLiveData<Int>().apply { value = 1 }

    val tvShowList = MediatorLiveData<MutableList<TvShow>>()

    override val goToTvShowEvent: MutableLiveData<Event<TvShow>> = MutableLiveData()

    init {
        loadedTvShowList = tvShowsPage.switchMap {
            liveDataBlockScope {
                tvShowRepository.loadDiscoverList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }
        tvShowList.addSource(loadedTvShowList) { it?.let { list -> tvShowList.appendList(list) } }
    }

    fun loadMoreTvShows() {
        tvShowsPage.value = tvShowsPage.value?.plus(1)
    }
}