package com.chiheb.fizzbuzz.core.di

import com.chiheb.fizzbuzz.data.repositories.ResultRepositoryImpl
import com.chiheb.fizzbuzz.domain.repositories.IResultRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindResultRepository(impl: ResultRepositoryImpl): IResultRepository
}