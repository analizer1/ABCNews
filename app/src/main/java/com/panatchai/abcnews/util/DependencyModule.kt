package com.panatchai.abcnews.util

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Build
import com.panatchai.abcnews.data.api.FeedApi
import com.panatchai.abcnews.data.db.FeedDb
import com.panatchai.abcnews.data.db.FeedDbInMemory
import com.panatchai.abcnews.data.repository.FeedRepository
import com.panatchai.abcnews.domain.usecase.NewsFeedUseCase
import com.panatchai.abcnews.domain.usecase.UseCase
import com.panatchai.abcnews.ui.model.NewsFeed
import com.panatchai.abcnews.ui.viewmodel.FeedViewModelFactory
import java.util.Locale

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 *
 * This just imitates the Dagger's Module since the test does not allow using other lib (e.g. Dagger2).
 */
class DependencyModule constructor(context: Context) {

    private val appExecutors = AppExecutors()
    private val feedApi = FeedApi(context.applicationContext)
    private val db: FeedDb = FeedDbInMemory()
    private val repository = FeedRepository(appExecutors, feedApi, db)
    private val newsFeedUseCase = NewsFeedUseCase(appExecutors, repository)
    private val viewModelFactory = FeedViewModelFactory(context.applicationContext)
    private val currentLocale =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            //noinspection deprecation
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }

    fun provideAppExecutors(): AppExecutors {
        return appExecutors
    }

    fun provideFeedApi(): FeedApi {
        return feedApi
    }

    fun provideFeedDb(): FeedDb {
        return db
    }

    fun provideFeedRepository(): FeedRepository {
        return repository
    }

    fun provideFeedListUseCase(): UseCase<Void, List<NewsFeed>> {
        return newsFeedUseCase
    }

    fun provideFeedViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }

    fun provideLocale(): Locale {
        return currentLocale
    }

    companion object {
        @Volatile
        private var INSTANCE: DependencyModule? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: DependencyModule(context).also {
                        INSTANCE = it
                    }
            }
    }
}