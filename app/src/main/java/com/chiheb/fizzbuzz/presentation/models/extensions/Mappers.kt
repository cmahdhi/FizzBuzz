package com.chiheb.fizzbuzz.presentation.models.extensions

import com.chiheb.fizzbuzz.domain.models.InputDomain
import com.chiheb.fizzbuzz.domain.models.ResultDomain
import com.chiheb.fizzbuzz.presentation.models.CalculationInput
import com.chiheb.fizzbuzz.presentation.models.Result

fun CalculationInput.toDomain() = InputDomain(
    firstDivider = this.firstDivider,
    secondDivider = this.secondDivider,
    firstReplacementText = this.firstReplacementText,
    secondReplacementText = this.secondReplacementText,
    calculationLimit = this.calculationLimit
)

fun ResultDomain.toModel() = Result(
    data = this.data,
    nextPage = this.nextPage,
    hasMoreItems = this.hasMoreItems
)

