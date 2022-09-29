package com.amg.marvel.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amg.config.NetworkStatus
import com.amg.domain.repository.CharactersRepository
import com.amg.domain.repository.ComicRepository
import com.amg.domain.useCases.GetCharacterUseCase
import com.amg.domain.useCases.GetComicsUseCase
import com.amg.marvel.MainCoroutineRule
import com.amg.marvel.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var charactersRepository: CharactersRepository
    @Mock
    private lateinit var comicRepository: ComicRepository
    @Mock
    private lateinit var getCharacterUseCase: GetCharacterUseCase
    @Mock
    private lateinit var getComicsUseCase: GetComicsUseCase
    @Mock
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var params: GetCharacterUseCase.Params

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getCharacterUseCase = GetCharacterUseCase(charactersRepository)
        getComicsUseCase = GetComicsUseCase(comicRepository)
        detailViewModel = DetailViewModel(getCharacterUseCase, getComicsUseCase)
        params = GetCharacterUseCase.Params(anyInt())

    }

    @Test
    fun testApiFetchDataIsLoading () = runTest {
        //GIVEN
        Mockito.`when`(getCharacterUseCase.invoke(params)).thenReturn(any())
        //WHEN
        detailViewModel.getCharacterDetail(anyInt())
        //THEN
        Assert.assertEquals(NetworkStatus.LOADING, detailViewModel.character.getOrAwaitValue().status)
    }

    @Test
    fun testApiFetchDataSuccess () = runTest {
        //GIVEN
        Mockito.`when`(getCharacterUseCase.invoke(params)).thenReturn(any())
        //WHEN
        detailViewModel.getCharacterDetail(anyInt())
        //THEN
        Assert.assertEquals(NetworkStatus.LOADING, detailViewModel.character.getOrAwaitValue().status)
        launch {
            Assert.assertEquals(NetworkStatus.SUCCESS, detailViewModel.character.getOrAwaitValue().status)
        }
    }

    @Test
    fun testApiFetchDataError () = runTest {
        //GIVEN
        Mockito.`when`(getCharacterUseCase.invoke(params)).thenThrow(NullPointerException::class.java)
        //WHEN
        detailViewModel.getCharacterDetail(anyInt())
        //THEN
        Assert.assertEquals(NetworkStatus.LOADING, detailViewModel.character.getOrAwaitValue().status)
        launch {
            Assert.assertEquals(NetworkStatus.SERVER_ERROR, detailViewModel.character.getOrAwaitValue().status)
        }
    }

}