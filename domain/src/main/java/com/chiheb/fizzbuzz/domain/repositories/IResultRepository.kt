package com.chiheb.fizzbuzz.domain.repositories

import com.chiheb.fizzbuzz.domain.models.InputDomain
import com.chiheb.fizzbuzz.domain.models.ResultDomain

interface IResultRepository {

    suspend fun getResult(input: InputDomain): Result<ResultDomain>
}