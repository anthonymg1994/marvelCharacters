package com.amg.data.repository

import com.amg.data.mappers.toComic
import com.amg.data.network.api.MarvelApiService
import com.amg.domain.model.comics.Comic
import com.amg.domain.repository.ComicRepository
import javax.inject.Inject

class GetComicsRepositoryImpl @Inject constructor(private val apiService: MarvelApiService): ComicRepository {
    override suspend fun getComics(id: Int): List<Comic> {
        val response = apiService.getCharacterComics(id)
        return response.body()?.data?.results?.toComic() ?: emptyList()
    }
}