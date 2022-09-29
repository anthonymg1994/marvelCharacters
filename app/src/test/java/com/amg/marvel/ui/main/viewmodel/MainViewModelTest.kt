package com.amg.marvel.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amg.config.NetworkStatus
import com.amg.config.Resource
import com.amg.domain.repository.CharactersRepository
import com.amg.domain.useCases.GetCharactersUseCase
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var charactersRepository: CharactersRepository
    @Mock
    private lateinit var getCharactersUseCase: GetCharactersUseCase
    @Mock
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getCharactersUseCase = GetCharactersUseCase(charactersRepository)
        mainViewModel = MainViewModel(getCharactersUseCase)
    }

    @Test
    fun testApiFetchDataIsLoading () = runTest {
        //GIVEN
        Mockito.`when`(getCharactersUseCase.invoke()).thenReturn(Resource.success(listOf()))
        //WHEN
        mainViewModel.getCharactersList()
        //THEN
        Assert.assertEquals(NetworkStatus.LOADING, mainViewModel.shows.getOrAwaitValue().status)
    }

    @Test
    fun testApiFetchDataSuccess () = runTest {
        //GIVEN
        Mockito.`when`(getCharactersUseCase.invoke()).thenReturn(any())
        //WHEN
        mainViewModel.getCharactersList()
        //THEN
        Assert.assertEquals(NetworkStatus.LOADING, mainViewModel.shows.getOrAwaitValue().status)
        launch {
            Assert.assertEquals(NetworkStatus.SUCCESS, mainViewModel.shows.getOrAwaitValue().status)
        }
    }

    @Test
    fun testApiFetchDataError () = runTest {
        //GIVEN
        Mockito.`when`(getCharactersUseCase.invoke()).thenThrow(NullPointerException::class.java)
        //WHEN
        mainViewModel.getCharactersList()
        //THEN
        Assert.assertEquals(NetworkStatus.LOADING, mainViewModel.shows.getOrAwaitValue().status)
        launch {
            Assert.assertEquals(NetworkStatus.SERVER_ERROR, mainViewModel.shows.getOrAwaitValue().status)
        }
    }
}
