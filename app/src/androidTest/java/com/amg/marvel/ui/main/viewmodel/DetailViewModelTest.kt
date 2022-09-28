package com.amg.marvel.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amg.config.NetworkStatus
import com.amg.config.Resource
import com.amg.domain.model.Thumbnail
import com.amg.domain.model.character.Character
import com.amg.domain.model.comics.Comic
import com.amg.domain.repository.CharactersRepository
import com.amg.domain.repository.ComicRepository
import com.amg.domain.useCases.GetCharacterUseCase
import com.amg.domain.useCases.GetComicsUseCase
import getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations

internal class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var comicRepository: ComicRepository

    @Mock
    private lateinit var charactersRepository: CharactersRepository

    private lateinit var getComicsUseCase: GetComicsUseCase

    private lateinit var getCharacterUseCase: GetCharacterUseCase

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    private lateinit var mockObserver: Observer<Character>


    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        getCharacterUseCase = GetCharacterUseCase(charactersRepository)
        getComicsUseCase = GetComicsUseCase(comicRepository)
        detailViewModel = DetailViewModel(getCharacterUseCase, getComicsUseCase)
        detailViewModel.singleCharacter.observeForever { mockObserver }
    }

}