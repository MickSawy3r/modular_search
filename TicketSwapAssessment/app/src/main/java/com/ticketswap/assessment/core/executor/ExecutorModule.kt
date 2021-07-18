package com.ticketswap.assessment.core.executor

import com.ticketswap.reactive.executor.PostExecutionThread
import com.ticketswap.reactive.executor.ThreadExecutor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExecutorModule {
    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread
}
