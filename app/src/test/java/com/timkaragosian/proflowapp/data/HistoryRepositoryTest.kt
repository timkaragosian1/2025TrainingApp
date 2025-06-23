package com.timkaragosian.proflowapp.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timkaragosian.proflowapp.data.db.AppDatabase
import com.timkaragosian.proflowapp.data.repository.HistoryRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HistoryRepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var repo: HistoryRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        repo = HistoryRepositoryImpl(db.historyDao())
    }

    @After
    fun tearDown(){
        db.close()
        Dispatchers.resetMain()
    }

    @Test
    fun insertAndObserveHistoryTest() = runTest(testDispatcher) {
        repo.save("First")

        val values = repo.observeHistory().first()

        assertEquals(1, values.size)
        assertEquals("First", values[0].inputText)
    }
}