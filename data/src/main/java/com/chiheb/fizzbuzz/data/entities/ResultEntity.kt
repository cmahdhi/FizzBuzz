package com.chiheb.fizzbuzz.data.entities

data class ResultEntity(
    val data: List<String>,
    val nextPage: Int,
    val hasMoreItems: Boolean
)