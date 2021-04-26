package com.codingwithmitch.espressodaggerexamples.ui

import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.repository.FakeMainRepositoryImpl
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
abstract class BaseMainActivityTest {

    abstract fun injectTest(application: TestBaseApplication)

    fun configureFakeApiService(
        blogsDataSource: String? = null,
        categoriesDataSource: String? = null,
        networkDelay: Long? = null,
        application: TestBaseApplication
    ): FakeApiService
    {
        val apiService = (application.appComponent as TestAppComponent).apiService
        blogsDataSource?.let { apiService.blogPostJsonFileName = it }
        categoriesDataSource?.let { apiService.categoryJsonFileName = it }
        networkDelay?.let { apiService.networkDelay = it }
        return apiService
    }

    fun configureFakeRepository(
        apiService: FakeApiService,
        application: TestBaseApplication
    ): FakeMainRepositoryImpl
    {
        val mainRepository = (application.appComponent as TestAppComponent).mainRepository
        mainRepository.apiService = apiService
        return mainRepository
    }

}