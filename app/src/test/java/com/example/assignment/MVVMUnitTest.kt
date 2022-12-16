package com.example.assignment

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emedinaa.kotlinmvvm.capture
import com.example.assignment.data.OperationCallback
import com.example.assignment.model.MeteorData
import com.example.assignment.model.MeteorDataSource
import com.example.assignment.model.MeteorRepository
import com.example.assignment.viewmodel.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


/**
 * @author Eduardo Medina
 */
class MVVMUnitTest {

    @Mock
    private lateinit var meteorDataSource: MeteorDataSource

    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<MeteorData>>

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: MeteorRepository

    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderMeteorsObserver: Observer<List<MeteorData>>

    private lateinit var meteorEmptyList: List<MeteorData>
    private lateinit var meteorList: List<MeteorData>


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(context.applicationContext).thenReturn(context)

        repository = MeteorRepository(meteorDataSource)
        viewModel = MainViewModel(repository)

        mockData()
        setupObservers()
    }

    @Test
    fun `retrieve meteors with ViewModel and Repository returns empty data`() {
        with(viewModel) {
            getMeteorsInfo(context)
            isEmptyList.observeForever(emptyListObserver)
            meteors.observeForever(onRenderMeteorsObserver)
        }

        verify(meteorDataSource, times(1)).retrieveMeteor(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(meteorEmptyList)

        Assert.assertTrue(viewModel.isEmptyList.value == true)
        Assert.assertTrue(viewModel.meteors.value?.size == 0)
    }

    @Test
    fun `retrieve meteors with ViewModel and Repository returns full data`() {
        with(viewModel) {
            getMeteorsInfo(context)
            meteors.observeForever(onRenderMeteorsObserver)
        }

        verify(meteorDataSource, times(1)).retrieveMeteor(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(meteorList)

        Assert.assertTrue(viewModel.meteors.value?.size == 1)
    }

    @Test
    fun `retrieve meteors with ViewModel and Repository returns an error`() {
        with(viewModel) {
            getMeteorsInfo(context)
            onMessageError.observeForever(onMessageErrorObserver as Observer<in Any?>)
        }
        verify(meteorDataSource, times(1)).retrieveMeteor(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers() {
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        onRenderMeteorsObserver = mock(Observer::class.java) as Observer<List<MeteorData>>
    }

    private fun mockData() {
        meteorEmptyList = emptyList()
        val mockList: MutableList<MeteorData> = mutableListOf()
        mockList.add(
            MeteorData(
                "1",
                21.0,
                "Aachen",
                "50.775000",
                "6.083330",
                "1880-01-01T00:00:00.000"

            )
        )

        meteorList = mockList.toList()
    }
}