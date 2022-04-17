package com.sercroft.movieStack.ui.person_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sercroft.movieStack.data.db.repository.PersonRepository
import com.sercroft.movieStack.data.model.Event
import com.sercroft.movieStack.data.model.GoToCredit
import com.sercroft.movieStack.data.model.GoToImage
import com.sercroft.movieStack.data.model.entity.Credit
import com.sercroft.movieStack.data.model.entity.Image
import com.sercroft.movieStack.data.model.entity.Person
import com.sercroft.movieStack.ui.BaseViewModel
import com.sercroft.movieStack.util.extension.liveDataBlockScope

class PersonDetailsViewModelFactory(private val personId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonDetailsViewModel(personId) as T
    }
}

class PersonDetailsViewModel(personId: Int) : BaseViewModel(), GoToImage, GoToCredit {

    private val personRepository = PersonRepository()
    val person: LiveData<Person>
    val imageList: LiveData<List<Image>>
    val creditList: LiveData<List<Credit>>

    override val goToImageEvent: MutableLiveData<Event<Image>> = MutableLiveData()
    override val goToCreditEvent: MutableLiveData<Event<Credit>> = MutableLiveData()

    init {
        person = liveDataBlockScope {
            personRepository.loadDetails(personId) { mSnackBarText.postValue(Event(it)) }
        }

        imageList = liveDataBlockScope {
            personRepository.loadImages(personId) { mSnackBarText.postValue(Event(it)) }
        }

        creditList = liveDataBlockScope {
            personRepository.loadCredits(personId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}