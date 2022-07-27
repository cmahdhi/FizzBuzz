package com.chiheb.fizzbuzz.domain

interface BaseUseCase<P, R> {
    suspend operator fun invoke(param: P): Result<R>
}