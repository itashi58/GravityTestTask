package com.example.gravitytesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gravitytesttask.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> = _url

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val localHomeData = repository.getHome()
            _url.postValue(localHomeData ?: repository.getLinkFromApi())
        }
    }

}