package com.ticketswap.assessment.core.di

import android.content.Context
import com.ticketswap.authenticator.AuthGuard
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAuthGuard(@ApplicationContext context: Context): AuthGuard {
        return AuthGuard(context)
    }
}
