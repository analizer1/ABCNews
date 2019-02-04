package com.panatchai.abcnews.util

import java.util.concurrent.Executor

/**
 * Execute task immediately on the current thread.
 */
class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}
