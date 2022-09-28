package com.amg.data.repository

import com.amg.data.mappers.toCharacter
import com.amg.data.network.api.MarvelApiService
import com.amg.domain.model.character.Character
import com.amg.domain.model.Thumbnail
import com.amg.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharactersRepositoryImpl @Inject constructor(private val apiService: MarvelApiService): CharactersRepository {
    override suspend fun getCharacters(): List<Character> {
        val response = apiService.getCharacters()
        return response.body()?.data?.results?.toCharacter() ?: emptyList()
    }

    override suspend fun getDetail(id: Int): Character {
        val response = apiService.getDetailCharacter(id)
        return response.body()?.data?.results?.toCharacter()?.first() ?: Character(0,"","", "",Thumbnail("","", ""))
    }
}