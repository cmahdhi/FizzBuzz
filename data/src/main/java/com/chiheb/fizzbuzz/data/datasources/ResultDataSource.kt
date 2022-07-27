package com.chiheb.fizzbuzz.data.datasources

import com.chiheb.fizzbuzz.data.entities.InputData
import com.chiheb.fizzbuzz.data.entities.ResultEntity
import javax.inject.Inject
import kotlin.math.min

private const val PAGE_SIZE = 100

class ResultDataSource @Inject constructor() {

    fun calculateResult(input: InputData): ResultEntity {
        with(input) {
            val startRange = input.pageNumber.dec() * PAGE_SIZE + 1
            val endRange = min(input.pageNumber.dec() * PAGE_SIZE + PAGE_SIZE, input.calculationLimit)

            val list = if (startRange > endRange) emptyList() else (startRange..endRange).map {
                when {
                    it.isDividedBy(firstDivider) && it.isDividedBy(secondDivider) -> firstReplacementText + secondReplacementText
                    it.isDividedBy(firstDivider) -> firstReplacementText
                    it.isDividedBy(secondDivider) -> secondReplacementText
                    else -> it.toString()
                }
            }

            return ResultEntity(
                data = list,
                nextPage = input.pageNumber.inc(),
                hasMoreItems = list.isNotEmpty()
            )
        }
    }

    private fun Int.isDividedBy(divider: Int) = this % divider == 0
}