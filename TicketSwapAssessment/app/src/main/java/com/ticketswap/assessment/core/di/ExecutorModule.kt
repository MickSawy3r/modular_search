package com.ticketswap.assessment.core.di

import com.ticketswap.assessment.core.executor.JobExecutor
import com.ticketswap.assessment.core.executor.PostExecutionThread
import com.ticketswap.assessment.core.executor.ThreadExecutor
import com.ticketswap.assessment.core.executor.UIThread
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
