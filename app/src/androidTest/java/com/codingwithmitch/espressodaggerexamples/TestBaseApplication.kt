package com.codingwithmitch.espressodaggerexamples

import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class TestBaseApplication : BaseApplication(){

    override fun initAppComponent() {
        appComponent = DaggerTestAppComponent.builder()
            .application(this)
            .build()
    }
}