package com.example.assignment.di

import com.example.assignment.data.ApiClient
import com.example.assignment.data.MeteorRemoteDataSource
import com.example.assignment.model.MeteorDataSource
import com.example.assignment.model.MeteorRepository
import com.example.assignment.viewmodel.ViewModelFactory

object Injection {

    private var meteorDataSource: MeteorDataSource? = null
    private var meteorRepository: MeteorRepository? = null
    private var meteorViewModelFactory: ViewModelFactory? = null

    private fun createMeteorDataSource(): MeteorDataSource {
        val dataSource = MeteorRemoteDataSource(ApiClient)
        meteorDataSource = dataSource
        return dataSource
    }

    private fun createMeteorRepository(): MeteorRepository {
        val repository = MeteorRepository(provideDataSource())
        meteorRepository = repository
        return repository
    }

    private fun createFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        meteorViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = meteorDataSource ?: createMeteorDataSource()
    private fun providerRepository() = meteorRepository ?: createMeteorRepository()

    fun provideViewModelFactory() = meteorViewModelFactory ?: createFactory()

    fun destroy() {
        meteorDataSource = null
        meteorRepository = null
        meteorViewModelFactory = null
    }
}