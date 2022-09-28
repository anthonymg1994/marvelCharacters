package com.amg.domain.model.comics

data class ResponseComic(
    val code: Int,
    val eTag: String,
    val data: DataComic
)