package com.amg.domain.repository

import com.amg.domain.model.character.Character


interface CharactersRepository {
    suspend fun getCharacters(): List<Character>
    suspend fun getDetail(id: Int): Character
}