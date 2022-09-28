package com.amg.data.network.model.character

data class DataDTO (
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<CharacterDTO>?
)