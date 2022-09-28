package com.amg.data.network.model.comics


data class DataComicDTO (
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<ComicDTO>?
)