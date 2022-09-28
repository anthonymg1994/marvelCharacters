package com.amg.data.network.api

import com.amg.data.network.model.character.ResponseDTO
import com.amg.data.network.model.comics.ResponseComicDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters() : Response<ResponseDTO>

    @GET("v1/public/characters/{charactersId}")
    suspend fun getDetailCharacter(@Path("charactersId") id: Int) : Response<ResponseDTO>


    @GET("v1/public/characters/{charactersId}/comics")
    suspend fun getCharacterComics(
        @Path("charactersId") id: Int) : Response<ResponseComicDTO>
}