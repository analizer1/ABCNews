package com.panatchai.abcnews.domain.usecase

import android.arch.lifecycle.LiveData
import android.support.annotation.Nullable
import com.panatchai.abcnews.data.model.Resource

/**
 * A Generic UseCase Which Executing a task using the Command Pattern.
 *
 * @param <REQUEST>  a request type
 * @param <RESPONSE> a response type
</RESPONSE></REQUEST> */
abstract class UseCase<REQUEST, RESPONSE> {
    /**
     * Start running task without any parameters.
     *
     */
    fun run(): LiveData<Resource<RESPONSE>> {
        return executeUseCase(null)
    }

    /**
     * Override this to execute your own task.
     *
     * @param requestValues task's parameter if any.
     * @return [Resource] of a result.
     */
    protected abstract fun executeUseCase(@Nullable requestValues: REQUEST?): LiveData<Resource<RESPONSE>>
}
