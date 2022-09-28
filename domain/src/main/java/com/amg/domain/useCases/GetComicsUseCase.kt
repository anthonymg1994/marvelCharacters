package com.amg.domain.useCases

import com.amg.config.Resource
import com.amg.domain.model.comics.Comic
import com.amg.domain.repository.ComicRepository
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(private val comicRepository: ComicRepository) {
    suspend operator fun invoke(params: Params) : Resource<List<Comic>> {
        return try{
            val response = comicRepository.getComics(params.id)
            Resource.success(response)
        }catch (e:Exception){
            Resource.error(e.message.toString(), emptyList())
        }
    }
    data class Params(val id: Int)
}