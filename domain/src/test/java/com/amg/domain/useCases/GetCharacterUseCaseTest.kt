package com.amg.domain.useCases

import com.amg.config.NetworkStatus
import com.amg.domain.model.character.Character
import com.amg.domain.repository.CharactersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetCharacterUseCaseTest {
    @Mock
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var getCharacterUseCase: GetCharacterUseCase
    @Mock
    private lateinit var character : Character
    @Mock
    private lateinit var params : GetCharacterUseCase.Params

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getCharacterUseCase = GetCharacterUseCase(charactersRepository)
    }

    @Test
    fun `Given Characters When UseCase fetchCharacter returns success`() = runBlocking() {
        //GIVEN
        Mockito.`when`(charactersRepository.getDetail(anyInt())).thenReturn(character)
        //WHEN
        val output = getCharacterUseCase.invoke(params)
        //THEN
        Assert.assertEquals(NetworkStatus.SUCCESS, output.status)
    }

    @Test
    fun `Given Characters When UseCase fetchCharacter returns error`() = runBlocking() {
        //GIVEN
        Mockito.`when`(charactersRepository.getDetail(anyInt())).thenThrow(NullPointerException::class.java)
        //WHEN
        val output = getCharacterUseCase.invoke(params)
        //THEN
        Assert.assertEquals(NetworkStatus.SERVER_ERROR, output.status)
    }
}