package com.chiheb.fizzbuzz.domain.interactors

import com.chiheb.fizzbuzz.domain.BaseUseCase
import com.chiheb.fizzbuzz.domain.models.InputDomain
import com.chiheb.fizzbuzz.domain.models.ResultDomain
import com.chiheb.fizzbuzz.domain.repositories.IResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetResultUseCase @Inject constructor(
    private val repository: IResultRepository
) : BaseUseCase<InputDomain, ResultDomain> {

    override suspend fun invoke(param: InputDomain): Result<ResultDomain> =
        withContext(Dispatchers.IO) {
            repository.getResult(param)
        }
}