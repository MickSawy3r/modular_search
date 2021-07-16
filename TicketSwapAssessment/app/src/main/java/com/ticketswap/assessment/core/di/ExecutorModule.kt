package com.ticketswap.assessment.core.di

import com.ticketswap.executor.JobExecutor
import com.ticketswap.executor.PostExecutionThread
import com.ticketswap.executor.ThreadExecutor
import com.ticketswap.executor.UIThread
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExecutorModule {
    @Binds
    abstract fun bindThreadExecutor(jobExecutor: com.ticketswap.executor.JobExecutor): com.ticketswap.executor.ThreadExecutor

    @Binds
    abstract fun bindPostExecutionThread(uiThread: com.ticketswap.executor.UIThread): com.ticketswap.executor.PostExecutionThread
}
