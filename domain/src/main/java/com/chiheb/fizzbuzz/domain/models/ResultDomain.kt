package com.chiheb.fizzbuzz.domain.models

data class ResultDomain(
    val data: List<String>,
    val nextPage: Int,
    val hasMoreItems: Boolean
)