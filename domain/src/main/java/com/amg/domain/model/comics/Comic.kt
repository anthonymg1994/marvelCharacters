package com.amg.domain.model.comics

import com.amg.domain.model.Thumbnail

data class Comic (
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail
)