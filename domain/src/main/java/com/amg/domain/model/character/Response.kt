package com.amg.domain.model.character

data class Response(
    val code:Int,
    val eTag: String,
    val data: Data
)