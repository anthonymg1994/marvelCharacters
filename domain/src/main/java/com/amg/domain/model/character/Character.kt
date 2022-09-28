package com.amg.domain.model.character

import com.amg.domain.model.Thumbnail

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail
)