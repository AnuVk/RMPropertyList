package com.example.rmove.rmpropertylist.ui

import com.example.rmove.rmpropertylist.api.PropertyListApi
import com.example.rmove.rmpropertylist.model.PropertyDetails
import com.example.rmove.rmpropertylist.model.PropertyList
import com.example.rmove.rmpropertylist.utils.SchedulerHelper
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.util.concurrent.TimeUnit

class PropertyListPresenterTest {

    @Mock
    lateinit var mockPropertyListApi: PropertyListApi

    @Mock
    lateinit var mockView: PropertyListContract.PropertyView

    @Mock
    lateinit var mockSchedulerHelper: SchedulerHelper

    lateinit var mockPresenter: PropertyListContract.PropertyListPresenter
    lateinit var testScheduler: TestScheduler
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockPresenter = PropertyListPresenter(mockPropertyListApi, mockSchedulerHelper)
        mockPresenter.initialise(mockView)
        testScheduler = TestScheduler()
        Dispatchers.setMain(testDispatcher)
        `when`(mockSchedulerHelper.getIoScheduler()).thenReturn(testScheduler)
        `when`(mockSchedulerHelper.getMainThreadScheduler()).thenReturn(testScheduler)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given ApiReturnsEmptyList Then Error message method is called`() {
        `when`(mockPropertyListApi.getPropertyDetails()).thenReturn(
            Observable.just(
                PropertyList(
                    emptyList()
                )
            )
        )
        mockPresenter.getPropertyList()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        verify(mockView, times(1)).hideProgress()
        verify(mockView, times(1)).showErrorMessage(anyString())
        verify(mockView, never()).populateAverage(anyString())
    }

    @Test
    fun `given ApiReturnsValidList Then view must be populated for average`() {
        val mockPropertyData = mockTwoPropertyData()
        `when`(mockPropertyListApi.getPropertyDetails()).thenReturn(
            Observable.just(mockPropertyData)
        )
        mockPresenter.getPropertyList()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(mockView).populateAverage(anyString())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given ApiReturnsValidList Then view must be populated for average using coroutines`() {
        val mockPropertyData = mockTwoPropertyData()
        runBlockingTest {
            `when`(mockPropertyListApi.getPropertyDetailsByCo()).thenReturn(mockPropertyData)
            mockPresenter.getPropertyListByCo()

            verify(mockView).populateAverage(anyString())
        }
    }

    @Test
    fun `given ApiReturnsValidList Then view must be populatedWithAverageDetachedProperty`() {
        val mockPropertyData = mockFivePropertyData()
        `when`(mockPropertyListApi.getPropertyDetails()).thenReturn(
            Observable.just(mockPropertyData)
        )
        mockPresenter.getPropertyList()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(mockView).populateDetachedAverage(anyString())
    }

    @Test
    fun `given ApiCallFails Then Error message method is called`() {
        `when`(mockPropertyListApi.getPropertyDetails()).thenReturn(
            Observable.error(IOException("test"))
        )

        mockPresenter.getPropertyList()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(mockView, times(1)).hideProgress()
        verify(mockView, times(1)).showErrorMessage(anyString())
        verify(mockView, never()).populateAverage(anyString())
    }

    @Test
    fun verifyAverageIsCalculatedCorrectly() {

        val mockManyPropertyData = mockFivePropertyData()
        val actualAverage = mockPresenter.calculateAverage(mockManyPropertyData.properties)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        val expectedAverage = 255000.0.toBigDecimal()
        Assert.assertEquals(expectedAverage, actualAverage)

    }

    private fun mockTwoPropertyData(): PropertyList {
        return PropertyList(
            listOf(
                PropertyDetails(
                    1,
                    225000.00,
                    4,
                    2,
                    "2",
                    "london",
                    "w21as",
                    "paddington",
                    "Detached"
                ),
                PropertyDetails(
                    2,
                    150000.00,
                    4,
                    2,
                    "3",
                    "london",
                    "w21as",
                    "paddington",
                    "Semi detached"
                )
            )
        )
    }

    private fun mockFivePropertyData(): PropertyList {
        return PropertyList(
            listOf(
                PropertyDetails(
                    1,
                    225000.00,
                    4,
                    2,
                    "2",
                    "london",
                    "w21as",
                    "paddington",
                    "DETACHED"
                ),
                PropertyDetails(
                    2,
                    150000.00,
                    4,
                    2,
                    "3",
                    "london",
                    "w21as",
                    "paddington",
                    "detached"
                ),
                PropertyDetails(
                    3,
                    750000.00,
                    4,
                    2,
                    "3",
                    "london",
                    "w21as",
                    "paddington",
                    "detached"
                ),
                PropertyDetails(
                    4,
                    50000.00,
                    4,
                    2,
                    "3",
                    "london",
                    "w21as",
                    "paddington",
                    "DETACHED"
                ),
                PropertyDetails(
                    5,
                    100000.00,
                    4,
                    2,
                    "3",
                    "london",
                    "w21as",
                    "paddington",
                    "detached"
                )
            )
        )
    }

}