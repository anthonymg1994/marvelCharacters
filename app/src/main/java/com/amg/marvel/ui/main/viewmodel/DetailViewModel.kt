package com.amg.marvel.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amg.config.NetworkStatus
import com.amg.config.Resource
import com.amg.domain.model.character.Character
import com.amg.domain.model.comics.Comic
import com.amg.domain.useCases.GetCharacterUseCase
import com.amg.domain.useCases.GetComicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase, private val getComicsUseCase: GetComicsUseCase) : ViewModel(){
    private val _character: MutableLiveData<Resource<Character>> = MutableLiveData()
    val character: MutableLiveData<Resource<Character>> = _character
    private val _singleCharacter: MutableLiveData<Character> = MutableLiveData()
    val singleCharacter: MutableLiveData<Character> = _singleCharacter
    private val _comics: MutableLiveData<Resource<List<Comic>>> = MutableLiveData()
    val comics: MutableLiveData<Resource<List<Comic>>> = _comics

    private var handler = CoroutineExceptionHandler { _, exception ->
        _character.postValue(Resource.error(exception.message.toString(), null))
    }
    fun getCharacterDetail(id: Int)  = viewModelScope.launch(handler) {
        _character.postValue(Resource.loading(null))
        withContext(Dispatchers.IO){
            val result = getCharacterUseCase(GetCharacterUseCase.Params(id))
            _singleCharacter.postValue(result.data ?: null)
            _character.postValue(result)

            if(result.status === NetworkStatus.SUCCESS){
                val comics = getComicsUseCase(GetComicsUseCase.Params(id))
                _comics.postValue(comics)
            }
        }

    }
}