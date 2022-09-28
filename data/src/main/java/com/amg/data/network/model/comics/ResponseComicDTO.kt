package com.amg.data.network.model.comics


data class ResponseComicDTO (
    val code:Int?,
    val eTag: String?,
    val data: DataComicDTO?
    )
