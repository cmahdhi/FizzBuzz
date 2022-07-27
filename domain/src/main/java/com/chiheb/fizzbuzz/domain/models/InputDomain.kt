package com.chiheb.fizzbuzz.domain.models

data class InputDomain(
    val firstDivider: Int,
    val secondDivider: Int,
    val firstReplacementText: String,
    val secondReplacementText: String,
    val calculationLimit: Int,
    val pageNumber: Int = 1
)
