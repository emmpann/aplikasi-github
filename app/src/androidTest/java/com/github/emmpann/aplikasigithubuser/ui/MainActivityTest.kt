package com.github.emmpann.aplikasigithubuser.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.emmpann.aplikasigithubuser.R
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchUser() {
        onView(withId(R.id.searchBar)).perform(click())
    }

    @Test
    fun openFavoritePage() {
        onView(withId(R.id.menuFavorite)).perform(click())
    }
}