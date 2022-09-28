package com.amg.domain.useCases

import com.amg.config.NetworkStatus
import com.amg.domain.model.Thumbnail
import com.amg.domain.model.comics.Comic
import com.amg.domain.repository.ComicRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetComicsUseCaseTest {

    @Mock
    private lateinit var mockRepository: ComicRepository

    private lateinit var getComicsUseCase: GetComicsUseCase


    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getComicsUseCase = GetComicsUseCase(mockRepository)
    }

    @Test
    fun comicUseCase_success() : Unit =  runBlocking {
        val response  = getComicsUseCase(GetComicsUseCase.Params(anyInt()))
        assertEquals(NetworkStatus.SUCCESS, response.status)
    }

    @Test
    fun comicUseCase_return_items() : Unit =  runBlocking {
        val elements = listOf(
            Comic(1,"First", Thumbnail("url", "jpg"))
        )
        Mockito.`when`(mockRepository.getComics(anyInt())).thenReturn(elements)
        assertEquals(elements, getComicsUseCase(GetComicsUseCase.Params(anyInt())).data)
        verify(mockRepository).getComics(anyInt())
    }

    @Test
    fun comicUseCase_return_empty_list() : Unit =  runBlocking {
        Mockito.`when`(mockRepository.getComics(1111111)).thenReturn(emptyList())
        assertEquals(listOf<Comic>(), getComicsUseCase(GetComicsUseCase.Params(1111111)).data)
    }

}