package com.langga.moviecatalog.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.langga.moviecatalog.R
import com.langga.moviecatalog.utils.DataLocal
import com.langga.moviecatalog.utils.EspressoIdlingResource
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test


class HomeActivityTest {

    private val dataLocalMovie = DataLocal.generateLocalMovies()
    private val dataLocalTvShow = DataLocal.generateLocalTvShow()

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun changeSetting() {
        onView(withId(R.id.setting_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.setting_menu)).perform(click())

        onView(withId(R.id.switch_theme)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_theme)).perform(click())

        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).perform(click())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).perform(visibility(true))
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataLocalMovie.size)
        )
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dataLocalTvShow.size
            )
        )
    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.page_detail)).perform(swipeUp())
        onView(withId(R.id.iv_poster_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_popularity_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail)).check(matches(withText(dataLocalMovie[0].originalTitle)))

        onView(withId(R.id.tv_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_detail)).check(matches(withText(dataLocalMovie[0].releaseDate)))

        onView(withId(R.id.tv_rating_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_detail)).check(matches(withText(dataLocalMovie[0].voteAverage.toString())))

        onView(withId(R.id.description_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.description_detail)).check(matches(withText(dataLocalMovie[0].overview)))
    }

    @Test
    fun loadDetailTvShows() {
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click())
        )
        onView(withId(R.id.page_detail)).perform(swipeUp())
        onView(withId(R.id.iv_poster_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail)).check(matches(withText(dataLocalTvShow[0].originalName)))

        onView(withId(R.id.tv_popularity_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_detail)).check(matches(withText(dataLocalTvShow[0].firstAirDate)))

        onView(withId(R.id.tv_rating_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_detail)).check(matches(withText(dataLocalTvShow[0].voteAverage.toString())))

        onView(withId(R.id.description_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.description_detail)).check(matches(withText(dataLocalTvShow[0].overview)))

    }

    @Test
    fun loadFavoriteMovie() {
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite_film)).perform(click())
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.page_detail)).perform(swipeUp())
        onView(withId(R.id.iv_background_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_poster_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_popularity_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.description_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTvShow(){
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite_film)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.page_detail)).perform(swipeUp())
        onView(withId(R.id.iv_poster_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_popularity_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.description_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun emptyDataMovie() {
        onView(withId(R.id.loading_in_movie)).perform(visibility(true))
        onView(withId(R.id.tv_message_movie)).perform(visibility(true))
        onView(withId(R.id.rv_movies)).perform(visibility(false))
    }

    @Test
    fun emptyDataTvShow() {
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.loading_in_tv)).perform(visibility(true))
        onView(withId(R.id.tv_message_tv_show)).perform(visibility(true))
        onView(withId(R.id.rv_tv_shows)).perform(visibility(false))
    }

    private fun visibility(value: Boolean): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(View::class.java)
            }

            override fun perform(uiController: UiController?, view: View) {
                view.visibility = if (value) View.VISIBLE else View.GONE
            }

            override fun getDescription(): String {
                return "Show / Hide View"
            }
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }


}