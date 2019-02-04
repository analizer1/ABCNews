package com.panatchai.abcnews.ui.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.panatchai.abcnews.FeedApp
import com.panatchai.abcnews.R
import com.panatchai.abcnews.data.model.Resource
import com.panatchai.abcnews.ui.common.RetryCallback
import com.panatchai.abcnews.ui.model.NewsFeed
import com.panatchai.abcnews.ui.viewmodel.FeedListViewModel
import com.panatchai.abcnews.util.InstantAppExecutors
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@Suppress("TestFunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(application = FeedApp::class)
class FeedListActivityTest {

    private lateinit var controller: ActivityController<FeedListActivity>

    private val viewModelFactory = mock<ViewModelProvider.Factory>()
    private val viewModel = mock<FeedListViewModel>()

    @Before
    fun setup() {
        whenever(viewModelFactory.create<FeedListViewModel>(any())).thenReturn(viewModel)

        controller = Robolectric.buildActivity(FeedListActivity::class.java)
        val activity = controller.get()
        activity.appExecutors = InstantAppExecutors()
        activity.viewModelFactory = viewModelFactory
    }

    @Test
    fun implementRefreshableView() {
        // init mocks
        val liveData = MutableLiveData<Resource<List<NewsFeed>>>()
        whenever(viewModel.refreshFeeds()).thenReturn(liveData)
        liveData.value = Resource.loading(null)

        // execute test
        val activity = controller.get()

        // verify
        assertTrue(activity is RefreshableView)

        // clean up
        controller.create().start().resume().pause().stop().destroy()
    }

    @Test
    fun implementRetryCallback() {
        // init mocks
        val liveData = MutableLiveData<Resource<List<NewsFeed>>>()
        whenever(viewModel.refreshFeeds()).thenReturn(liveData)
        liveData.value = Resource.loading(null)

        // execute test
        val activity = controller.get()

        // verify
        assertTrue(activity is RetryCallback)

        // clean up
        controller.create().start().resume().pause().stop().destroy()
    }

    @Test
    fun WhenRetryClicked_ThenRetry() {
        // init mocks
        val liveData = MutableLiveData<Resource<List<NewsFeed>>>()
        whenever(viewModel.refreshFeeds()).thenReturn(liveData)
        liveData.value = Resource.error("error", null)

        // execute test
        val activity = controller.create().start().resume().visible().get()

        // verify
        verify(viewModel, times(1)).refreshFeeds() // onResume() x 1
        val retryBtn = activity.findViewById<Button>(R.id.retry)
        assertNotNull(retryBtn)
        assertThat(retryBtn.visibility, `is`(View.VISIBLE))

        // execute test
        val performClick = retryBtn.performClick()

        // verify
        assertTrue(performClick)
        verify(viewModel, times(2)).refreshFeeds() // retry() x 2

        // clean up
        controller.pause().stop().destroy()
    }

    @Test
    fun WhenOnResume_ThenRefresh() {
        // init mocks
        val liveData = MutableLiveData<Resource<List<NewsFeed>>>()
        whenever(viewModel.refreshFeeds()).thenReturn(liveData)
        liveData.value = Resource.success(null)

        // execute test
        controller.create().start().resume().visible().get()

        // verify
        verify(viewModel, times(1)).refreshFeeds()

        // clean up
        controller.pause().stop().destroy()
    }

    @Test
    fun WhenLoad_ShowLoading() {
        // init mocks
        val liveData = MutableLiveData<Resource<List<NewsFeed>>>()
        whenever(viewModel.refreshFeeds()).thenReturn(liveData)
        liveData.value = Resource.loading(null)

        // execute test
        val activity = controller.create().start().resume().visible().get()

        // verify
        val loadingContainer = activity.findViewById<LinearLayout>(R.id.loading_container)
        val errorTextView = activity.findViewById<TextView>(R.id.error_msg)
        val retry = activity.findViewById<Button>(R.id.retry)
        val progressBar = activity.findViewById<ProgressBar>(R.id.progress_bar)
        assertNotNull(errorTextView)
        assertNotNull(retry)
        assertNotNull(progressBar)
        assertNotNull(loadingContainer)
        assertThat(loadingContainer.visibility, `is`(View.VISIBLE)) // loading container should appear
        assertThat(progressBar.visibility, `is`(View.VISIBLE)) // should show progress bar
        assertThat(errorTextView.visibility, `is`(View.GONE)) // should not show error
        assertThat(retry.visibility, `is`(View.GONE)) // retry also should not show

        // clean up
        controller.pause().stop().destroy()
    }

    @Test
    fun WhenSuccess_ShowData() {
        // init mocks
        val liveData = MutableLiveData<Resource<List<NewsFeed>>>()
        whenever(viewModel.refreshFeeds()).thenReturn(liveData)
        val feed1 = NewsFeed("", "", "", "")
        val feed2 = NewsFeed("", "", "", "")
        liveData.value = Resource.success(listOf(feed1, feed2))

        // execute test
        val activity = controller.create().start().resume().visible().get()

        // verify
        val loadingContainer = activity.findViewById<LinearLayout>(R.id.loading_container)
        val feedList = activity.findViewById<RecyclerView>(R.id.feed_list)
        assertNotNull(loadingContainer)
        assertNotNull(feedList)
        assertThat(loadingContainer.visibility, `is`(View.GONE)) // loading container should not show
        assertThat(feedList.visibility, `is`(View.VISIBLE)) // should display feed list
        assertNotNull(feedList.adapter) // adapter should not be null
        assertThat(feedList.adapter?.itemCount, `is`(2)) // and contains feeds

        // clean up
        controller.pause().stop().destroy()
    }

    @Test
    fun WhenError_ShowError() {
        // init mocks
        val liveData = MutableLiveData<Resource<List<NewsFeed>>>()
        whenever(viewModel.refreshFeeds()).thenReturn(liveData)
        liveData.value = Resource.error("error", null)

        // execute test
        val activity = controller.create().start().resume().visible().get()

        // verify
        val loadingContainer = activity.findViewById<LinearLayout>(R.id.loading_container)
        val errorTextView = activity.findViewById<TextView>(R.id.error_msg)
        val retry = activity.findViewById<Button>(R.id.retry)
        val progressBar = activity.findViewById<ProgressBar>(R.id.progress_bar)
        assertNotNull(loadingContainer)
        assertNotNull(errorTextView)
        assertNotNull(retry)
        assertNotNull(progressBar)
        assertThat(loadingContainer.visibility, `is`(View.VISIBLE)) // loading container should be visible
        assertThat(errorTextView.visibility, `is`(View.VISIBLE)) // error should appear
        assertThat(retry.visibility, `is`(View.VISIBLE)) // retry button should also appear
        assertThat(progressBar.visibility, `is`(View.GONE)) // loading should be gone

        // clean up
        controller.pause().stop().destroy()
    }
}