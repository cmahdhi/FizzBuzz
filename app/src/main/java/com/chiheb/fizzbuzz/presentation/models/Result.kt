package com.chiheb.fizzbuzz.presentation.models

data class Result(
    val data: List<String>,
    val nextPage: Int,
    val hasMoreItems: Boolean
)