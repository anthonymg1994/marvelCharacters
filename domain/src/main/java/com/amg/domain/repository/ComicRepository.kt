package com.amg.domain.repository

import com.amg.domain.model.comics.Comic

interface ComicRepository {
    suspend fun getComics(id: Int): List<Comic>
}