package com.codingwithmitch.espressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@InternalCoroutinesApi
class MainNavigationTests : BaseMainActivityTest(){

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var jsonUtil: JsonUtil

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

    @Test
    fun basicNavigationTest() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)
        injectTest(app)

        val rawJson = jsonUtil.readJSONFromAsset(Constants.BLOG_POSTS_DATA_FILENAME)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>(){}.type
        )
        val SELECTED_LIST_INDEX = 8
        val selectedBlogPost = blogs[SELECTED_LIST_INDEX]

        // run test
        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.recycler_view))
        recyclerView.check(matches(isDisplayed()))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(SELECTED_LIST_INDEX)
        )
        // Nav to detail fragment
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<BlogPostListAdapter.BlogPostViewHolder>(SELECTED_LIST_INDEX, click())
        )
        onView(withId(R.id.blog_title))
            .check(matches(withText(selectedBlogPost.title)))

        onView(withId(R.id.blog_category))
            .check(matches(withText(selectedBlogPost.category)))

        onView(withId(R.id.blog_body))
            .check(matches(withText(selectedBlogPost.body)))

        // Nav to final fragment
        onView(withId(R.id.blog_image))
            .perform(click())

        onView(withId(R.id.scaling_image_view))
            .check(matches(isDisplayed()))

        //back to detail fragment
        pressBack()

        onView(withId(R.id.blog_title))
            .check(matches(withText(selectedBlogPost.title)))
        //back to listFragment
        pressBack()
        recyclerView.check(matches(isDisplayed()))
    }
}