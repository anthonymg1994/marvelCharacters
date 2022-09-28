package com.amg.domain.model.comics

data class DataComic (
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Comic>?
)