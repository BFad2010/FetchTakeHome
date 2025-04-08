package com.example.fetchhomeassignment.ui.vm

import app.cash.turbine.test
import com.example.fetchhomeassignment.data.FetchItemsResult
import com.example.fetchhomeassignment.data.FetchListItem
import com.example.fetchhomeassignment.data.db.FetchItemsEntity
import com.example.fetchhomeassignment.data.db.FetchItemsRepo
import com.example.fetchhomeassignment.data.service.FetchItemsServiceAdapter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class FetchItemsViewModelTest {
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private var mockFetchItemsServiceAdapter: FetchItemsServiceAdapter = mockk()
    private var mockFetchItemsRepo: FetchItemsRepo = mockk()
    private lateinit var mockFetchItemsViewModel: FetchItemsViewModel

    @get:Rule
    val mockkRule = MockKRule(this)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockFetchItemsViewModel = FetchItemsViewModel(
            fetchItemsServiceAdapter = mockFetchItemsServiceAdapter,
            fetchItemsRepo = mockFetchItemsRepo,
            dispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `test retrieveAllFetchItems SUCCESS from service`() = runTest {
        val testItems = getTestFetchItmes()
        val expectedResponse = FetchItemsResult.Success(testItems)
        val expectedState = UiState.CONTENT(
            testItems.groupBy { it.listId }.toSortedMap()
        )
        coEvery { mockFetchItemsRepo.getAllItems() } returns emptyList()
        coEvery { mockFetchItemsRepo.deleteAllItems() } just runs
        coEvery { mockFetchItemsRepo.insertItems(any()) } just runs
        coEvery { mockFetchItemsServiceAdapter.getFetchItems() } returns expectedResponse

        mockFetchItemsViewModel.uiState.test {
            assert(awaitItem() is UiState.LOADING)
            mockFetchItemsViewModel.retrieveAllFetchItems()
            val state = awaitItem()
            coVerify {
                mockFetchItemsRepo.getAllItems()
                mockFetchItemsServiceAdapter.getFetchItems()
            }
            assert(state is UiState.CONTENT)
            assert(state.equals(expectedState))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test retrieveAllFetchItems FAILURE from service`() = runTest {
        val testFailureMessage = "test failure message"
        val expectedResponse = FetchItemsResult.Failure(testFailureMessage)
        val expectedState = UiState.ERROR(testFailureMessage)
        coEvery { mockFetchItemsRepo.getAllItems() } returns emptyList()
        coEvery { mockFetchItemsRepo.deleteAllItems() } just runs
        coEvery { mockFetchItemsRepo.insertItems(any()) } just runs
        coEvery { mockFetchItemsServiceAdapter.getFetchItems() } returns expectedResponse

        mockFetchItemsViewModel.uiState.test {
            assert(awaitItem() is UiState.LOADING)
            mockFetchItemsViewModel.retrieveAllFetchItems()
            val state = awaitItem()
            coVerify {
                mockFetchItemsServiceAdapter.getFetchItems()
            }
            assert(state is UiState.ERROR)
            assert(state.equals(expectedState))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test retrieveAllFetchItems SUCCESS from Database`() = runTest {
        val testItems = getTestFetchItmes()
        val testEntities = testItems.map {
            FetchItemsEntity(
                it.id.toString(),
                it.listId,
                it.name.orEmpty()
            )
        }
        val expectedState = UiState.CONTENT(
            testItems.groupBy { it.listId }.toSortedMap()
        )
        coEvery { mockFetchItemsRepo.getAllItems() } returns testEntities
        coEvery { mockFetchItemsRepo.deleteAllItems() } just runs
        coEvery { mockFetchItemsRepo.insertItems(any()) } just runs

        mockFetchItemsViewModel.uiState.test {
            assert(awaitItem() is UiState.LOADING)
            mockFetchItemsViewModel.retrieveAllFetchItems()
            val state = awaitItem()
            coVerify {
                mockFetchItemsRepo.getAllItems()
            }
            assert(state is UiState.CONTENT)
            assert(state.equals(expectedState))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test retrieveAllFetchItems FAILURE from Database`() = runTest {
        val testItems = getTestFetchItmes()
        val expectedResponse = FetchItemsResult.Success(testItems)
        val expectedState = UiState.CONTENT(
            testItems.groupBy { it.listId }.toSortedMap()
        )
        coEvery { mockFetchItemsRepo.getAllItems() } throws Exception("Fail from database")
        coEvery { mockFetchItemsRepo.deleteAllItems() } just runs
        coEvery { mockFetchItemsRepo.insertItems(any()) } just runs
        coEvery { mockFetchItemsServiceAdapter.getFetchItems() } returns expectedResponse

        mockFetchItemsViewModel.uiState.test {
            assert(awaitItem() is UiState.LOADING)
            mockFetchItemsViewModel.retrieveAllFetchItems()
            val state = awaitItem()
            coVerify {
                mockFetchItemsRepo.getAllItems()
                mockFetchItemsServiceAdapter.getFetchItems()
            }
            assert(state is UiState.CONTENT)
            assert(state.equals(expectedState))
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        private fun getTestFetchItmes(): List<FetchListItem> = listOf(
            FetchListItem(
                1,
                "1",
                "test one",
            ),
            FetchListItem(
                2,
                "1",
                "test two",
            ),
            FetchListItem(
                2,
                "3",
                "test three",
            )
        )
    }
}