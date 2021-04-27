package com.codingwithmitch.espressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@InternalCoroutinesApi
class ListFragmentErrorTests: BaseMainActivityTest(){

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun isErrorDialogShown_UnknowError() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = Constants.SERVER_ERROR_FILENAME,
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)
        injectTest(app)

        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error))
            .check(matches(isDisplayed()))

        onView(withSubstring(Constants.UNKNOWN_ERROR))
            .check(matches(isDisplayed()))
    }

    @Test
    fun doesNetworkTimeout_networkTimeoutError() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 4000L,
            application = app
        )

        configureFakeRepository(apiService, app)
        injectTest(app)

        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error))
            .check(matches(isDisplayed()))

        onView(withSubstring(Constants.NETWORK_ERROR_TIMEOUT))
            .check(matches(isDisplayed()))
    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

}