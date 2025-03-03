package com.example.newsnewshare

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import com.example.newsnewshare.ui.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(AndroidJUnit4::class)
class AuthLoginInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)
    @Test
    fun checkLogin() {
        onView(withId(R.id.edittabusernamee)).perform(
            typeText("")
        )

        Thread.sleep(1500)
        onView(withId(R.id.edittabpassword)).perform(
            typeText("")
        )

        closeSoftKeyboard()

        Thread.sleep(1500)

        onView(withId(R.id.btnlogin)).perform(
            click()
        )

        Thread.sleep(3000)
        onView(withId(R.id.displayLoginn)).check(matches(withText("login failed")))
    }

}