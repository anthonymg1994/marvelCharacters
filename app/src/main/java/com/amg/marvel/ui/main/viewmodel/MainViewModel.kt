package com.amg.marvel.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amg.config.Resource
import com.amg.domain.model.character.Character
import com.amg.domain.useCases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) : ViewModel(){
    private val _shows: MutableLiveData<Resource<List<Character>>> = MutableLiveData()
    val shows: MutableLiveData<Resource<List<Character>>> = _shows

    private var handler = CoroutineExceptionHandler { _, exception ->
        _shows.postValue(Resource.error(exception.message.toString(), null))
    }
    fun getCharactersList()  = viewModelScope.launch(handler) {
        _shows.postValue(Resource.loading(null))
       val result = withContext(Dispatchers.IO){
            getCharactersUseCase()
        }
        _shows.postValue(result)
    }
}