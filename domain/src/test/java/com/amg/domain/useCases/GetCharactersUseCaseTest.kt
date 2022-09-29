package com.amg.domain.useCases

import com.amg.config.NetworkStatus
import com.amg.domain.repository.CharactersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class GetCharactersUseCaseTest {
    @Mock
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setUp(){
     MockitoAnnotations.openMocks(this)
        getCharactersUseCase = GetCharactersUseCase(charactersRepository)
    }

    @Test
    fun `Given Characters When UseCase fetchCharacters returns success`() = runBlocking() {
        //GIVEN
        Mockito.`when`(charactersRepository.getCharacters()).thenReturn(listOf())
        //WHEN
        val output = getCharactersUseCase.invoke()
        //THEN
        assertEquals( NetworkStatus.SUCCESS, output.status)
    }

    @Test
    fun `Given Characters When UseCase fetchCharacters returns error`() = runBlocking() {
        //GIVEN
        Mockito.`when`(charactersRepository.getCharacters()).thenThrow(NullPointerException::class.java)
        //WHEN
        val output = getCharactersUseCase.invoke()
        //THEN
        assertEquals(NetworkStatus.SERVER_ERROR, output.status)
    }
}