package com.amg.domain.useCases

import com.amg.config.Resource
import com.amg.domain.model.character.Character
import com.amg.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke() : Resource<List<Character>> {
        return try{
            val response = charactersRepository.getCharacters()
            Resource.success(response)
        }catch (e:Exception){
            Resource.error(e.message.toString(), emptyList())
        }
    }
}