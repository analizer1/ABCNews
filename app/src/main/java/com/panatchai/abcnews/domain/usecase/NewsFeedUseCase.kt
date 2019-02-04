package com.panatchai.abcnews.domain.usecase

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.panatchai.abcnews.data.mapper.map
import com.panatchai.abcnews.data.model.Resource
import com.panatchai.abcnews.data.repository.FeedRepository
import com.panatchai.abcnews.ui.model.NewsFeed
import com.panatchai.abcnews.util.AppExecutors

/**
 * Domain-layer usecase responsible for getting feeds
 * and applying any business logic.
 *
 * Note: In this simple app, we may get rid of the usecase
 *       and inject the repository directly into ViewModel.
 */
class NewsFeedUseCase(
    private val appExecutors: AppExecutors,
    private val repository: FeedRepository
) : UseCase<Void, List<NewsFeed>>() {

    private val transformLiveData = MediatorLiveData<Resource<List<NewsFeed>>>()

    @Suppress("UNCHECKED_CAST")
    override fun executeUseCase(requestValues: Void?): LiveData<Resource<List<NewsFeed>>> {
        transformLiveData.addSource(repository.refresh()) { resource ->
            appExecutors.networkIO().execute {
                val feedList = resource?.data
                val map = feedList?.map()
                transformLiveData.postValue(Resource.success(map))
            }
        }
        return transformLiveData
    }
}