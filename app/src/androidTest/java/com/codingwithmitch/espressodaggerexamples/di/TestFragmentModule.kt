package com.codingwithmitch.espressodaggerexamples.di

import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.espressodaggerexamples.fragments.FakeMainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.util.FakeGlideRequestManager
import com.codingwithmitch.espressodaggerexamples.viewmodels.FakeMainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@Module
object TestFragmentModule {


    @JvmStatic
    @Singleton
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: FakeMainViewModelFactory,
        glideManager: FakeGlideRequestManager
    ): FragmentFactory {
        return FakeMainFragmentFactory(viewModelFactory, glideManager)
    }

}