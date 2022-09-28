package com.amg.data.mappers

import com.amg.data.network.model.character.CharacterDTO
import com.amg.data.network.model.character.DataDTO
import com.amg.data.network.model.character.ResponseDTO
import com.amg.data.network.model.ThumbnailDTO
import com.amg.data.network.model.comics.ComicDTO
import com.amg.data.network.model.comics.DataComicDTO
import com.amg.data.network.model.comics.ResponseComicDTO
import com.amg.domain.model.character.Character
import com.amg.domain.model.character.Data
import com.amg.domain.model.character.Response
import com.amg.domain.model.Thumbnail
import com.amg.domain.model.comics.Comic
import com.amg.domain.model.comics.DataComic
import com.amg.domain.model.comics.ResponseComic

fun ResponseDTO.toResponse(): Response {
    return Response(
            code = code ?: 0,
            eTag = eTag ?: "",
            data = data ?.toData() ?: Data(0,0,0,0, emptyList())
    )
}

fun DataDTO.toData() : Data {
    return Data(
        offset = offset ?: 0,
        limit = limit ?: 20,
        total = total ?: 0,
        count = count ?: 0,
        results = results ?.toCharacter() ?: emptyList()
    )
}

fun List<CharacterDTO>.toCharacter(): List<Character>{
   return map {
       Character(
           id = it.id ?: 0,
           name = it.name ?: "",
           description = it.description ?: "",
           modified = it.modified ?: "",
           thumbnail = it.thumbnail ?.toThumbnail()
       )
   }
}


fun ResponseComicDTO.toComicResponse(): ResponseComic {
    return ResponseComic(
        code = code ?: 0,
        eTag = eTag ?: "",
        data = data ?.toComicData() ?: DataComic(0,0,0,0, emptyList())
    )
}

fun DataComicDTO.toComicData() : DataComic {
    return DataComic(
        offset = offset ?: 0,
        limit = limit ?: 20,
        total = total ?: 0,
        count = count ?: 0,
        results = results ?.toComic() ?: emptyList()
    )
}

fun List<ComicDTO>.toComic(): List<Comic>{
    return map {
        Comic(
            id = it.id ?: 0,
            title = it.title ?: "",
            thumbnail = it.thumbnail ?.toThumbnail() ?: Thumbnail("","", "")
        )
    }
}

fun ThumbnailDTO.toThumbnail(): Thumbnail{
    return Thumbnail(
        path = path ?: "",
        extension = extension ?: "",
        completeUrl = "$path.$extension"
    )
}