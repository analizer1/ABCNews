package com.panatchai.abcnews.ui.viewmodel

import org.hamcrest.CoreMatchers.isA
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Suppress("TestFunctionName", "DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class FeedViewModelFactoryTest {

    @Test
    fun GivenViewModelClass_WhenCreateViewModel_ThenReturnInstantOfViewModel() {
        val factory = FeedViewModelFactory(RuntimeEnvironment.application)
        val viewModel = factory.create(FeedListViewModel::class.java)
        assertThat(viewModel, isA(FeedListViewModel::class.java))
    }
}