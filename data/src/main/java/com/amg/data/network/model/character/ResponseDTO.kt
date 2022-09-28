package com.amg.data.network.model.character

data class ResponseDTO(
    val code:Int?,
    val eTag: String?,
    val data: DataDTO?
)