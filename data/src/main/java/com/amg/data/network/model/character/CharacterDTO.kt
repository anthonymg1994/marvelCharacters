package com.amg.data.network.model.character

import com.amg.data.network.model.ThumbnailDTO

data class CharacterDTO(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val thumbnail: ThumbnailDTO

)