package com.amg.domain.useCases

import com.amg.config.Resource
import com.amg.domain.model.character.Character
import com.amg.domain.model.Thumbnail
import com.amg.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(params: Params) : Resource<Character> {
        return try{
            val response = charactersRepository.getDetail(params.id)
            Resource.success(response)
        }catch (e:Exception){
            Resource.error(e.message.toString(), Character(0,"","", "",Thumbnail("","", "")))
        }
    }

    data class Params(val id: Int)
}