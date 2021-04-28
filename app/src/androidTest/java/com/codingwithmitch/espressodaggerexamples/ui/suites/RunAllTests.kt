package com.codingwithmitch.espressodaggerexamples.ui.suites

import com.codingwithmitch.espressodaggerexamples.ui.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite


@InternalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainNavigationTests::class,
    ListFragmentNavigationTests::class,
    ListFragmentErrorTests::class,
    DetailFragmentTest::class,
    ListFragmentIntegrationTests::class
)
class RunAllTests