package com.example.rmove.rmpropertylist

import android.view.View
import androidx.core.view.isInvisible
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rmove.rmpropertylist.ui.PropertyActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.TimeoutException


@RunWith(AndroidJUnit4::class)
class PropertyActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(PropertyActivity::class.java)

    private val mockWebServer = MockWebServer()

    @Before
    @Throws(IOException::class, InterruptedException::class)
    fun setup() {
        val mockDataString = readFileWithoutNewLineFromResources("mockPropertyData.json")
        mockWebServer.enqueue(MockResponse().setBody(mockDataString))
        mockWebServer.start(8080)
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `givenIHaveValidListOfProperties_then_AverageIsCalculatedIsDisplayed`() {
        onView(withText("Calculate the average property price from API response")).check(
            matches(
                isDisplayed()
            )
        )
        onView(isRoot()).perform(waitUntilViewInvisible(R.id.progess_bar, 5000))
        onView(withText("All property average : 410280.77777777775")).check(matches(isDisplayed()))
    }

    @Test
    fun `givenIHaveValidListOfProperties_then_DetachedPropertyAverageIsCalculatedIsDisplayed`() {
        onView(withText("Calculate the average property price from API response")).check(
                matches(
                        isDisplayed()
                )
        )
        onView(isRoot()).perform(waitUntilViewInvisible(R.id.progess_bar, 5000))
        onView(withText("All detached property average : 505520.75")).check(matches(isDisplayed()))
    }

    inner class WaitUntilViewInvisible(private val viewId: Int, private val timeout: Long) :
        ViewAction {
        override fun getConstraints(): Matcher<View> {
            return any(View::class.java)
        }

        override fun getDescription(): String {
            return "Wait upto $timeout ms for view to be invisible"
        }

        override fun perform(uiController: UiController, view: View) {
            val endTime = System.currentTimeMillis() + timeout
            val viewMatcher: Matcher<View> = withId(viewId)
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    if (viewMatcher.matches(child) && child.isInvisible) {
                        return
                    }
                }
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                .withActionDescription(description)
                .withCause(TimeoutException("Waited $timeout ms"))
                .withViewDescription(HumanReadables.describe(view))
                .build()
        }
    }

    private fun waitUntilViewInvisible(id: Int, timeout: Long): ViewAction {
        return WaitUntilViewInvisible(id, timeout)
    }

    @Throws(IOException::class)
    fun readFileWithoutNewLineFromResources(fileName: String): String {
        var inputStream: InputStream? = null
        try {
            inputStream =
                javaClass.classLoader?.getResourceAsStream(fileName)
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))

            var str: String? = reader.readLine()
            while (str != null) {
                builder.append(str)
                str = reader.readLine()
            }
            return builder.toString()
        } finally {
            inputStream?.close()
        }
    }

}


