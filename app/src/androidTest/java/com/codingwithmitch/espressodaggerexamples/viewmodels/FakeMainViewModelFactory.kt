package com.codingwithmitch.espressodaggerexamples.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.espressodaggerexamples.repository.FakeMainRepositoryImpl
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@InternalCoroutinesApi
@Singleton
class FakeMainViewModelFactory
@Inject
constructor(
    private val mainRepositoryImpl: FakeMainRepositoryImpl
): ViewModelProvider.Factory

{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mainRepository = mainRepositoryImpl) as T
        }
        throw IllegalArgumentException("UnKnow viewmodel class")
    }

}