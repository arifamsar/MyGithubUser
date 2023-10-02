package com.arfsar.mygithubuser.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.arfsar.mygithubuser.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testAppBarAction() {
        runBlocking {
            delay(2000)
            onView(withId(R.id.menu_1)).perform(click())
            delay(1000)
            pressBack()
            delay(1000)
            onView(withId(R.id.menu_2)).perform(click())
        }
    }

    @Test
    fun testAddFavorite() {
        runBlocking {
            delay(2000)
            onView(withId(R.id.rv_users)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            )
            delay(2000)
            onView(withId(R.id.fab_favorite)).perform(click())
            delay(2000)
            pressBack()
            delay(1000)
            onView(withId(R.id.menu_1)).perform(click())
            delay(2000)
        }
    }
    @Test
    fun testRemoveFavorite() {
        runBlocking {
            delay(2000)
            onView(withId(R.id.rv_users)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            )
            delay(2000)
            onView(withId(R.id.fab_favorite)).perform(click())
            delay(2000)
            pressBack()
            delay(1000)
            onView(withId(R.id.menu_1)).perform(click())
            delay(2000)
            onView(withId(R.id.rvFavorite)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, click()
                )
            )
            delay(1000)
            pressBack()
            delay(1000)
        }
    }

}