package com.chiheb.fizzbuzz.data.entities.mappers

import com.chiheb.fizzbuzz.data.entities.InputData
import com.chiheb.fizzbuzz.data.entities.ResultEntity
import com.chiheb.fizzbuzz.domain.models.InputDomain
import com.chiheb.fizzbuzz.domain.models.ResultDomain

fun InputDomain.toEntity() = InputData(
    firstDivider = this.firstDivider,
    secondDivider = this.secondDivider,
    firstReplacementText = this.firstReplacementText,
    secondReplacementText = this.secondReplacementText,
    calculationLimit = this.calculationLimit,
    pageNumber = this.pageNumber
)

fun ResultEntity.toDomain() = ResultDomain(
    data = this.data,
    nextPage = this.nextPage,
    hasMoreItems = this.hasMoreItems
)