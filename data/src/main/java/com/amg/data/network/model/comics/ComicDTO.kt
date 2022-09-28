package com.amg.data.network.model.comics

import com.amg.data.network.model.ThumbnailDTO

data class ComicDTO (
    val id: Int?,
    val title: String?,
    val thumbnail: ThumbnailDTO?
)