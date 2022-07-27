package com.chiheb.fizzbuzz.data.repositories

import com.chiheb.fizzbuzz.data.datasources.ResultDataSource
import com.chiheb.fizzbuzz.data.entities.mappers.toDomain
import com.chiheb.fizzbuzz.data.entities.mappers.toEntity
import com.chiheb.fizzbuzz.domain.models.InputDomain
import com.chiheb.fizzbuzz.domain.models.ResultDomain
import com.chiheb.fizzbuzz.domain.repositories.IResultRepository
import javax.inject.Inject

class ResultRepositoryImpl @Inject constructor(private val dataSource: ResultDataSource) :
    IResultRepository {

    override suspend fun getResult(input: InputDomain): Result<ResultDomain> {
        return Result.success(dataSource.calculateResult(input.toEntity()).toDomain())
    }
}