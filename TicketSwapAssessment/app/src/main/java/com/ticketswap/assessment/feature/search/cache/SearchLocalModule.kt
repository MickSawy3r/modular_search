package com.ticketswap.assessment.feature.search.cache

import android.content.Context
import androidx.room.Room
import com.ticketswap.assessment.feature.search.datasource.local.ISearchCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SearchLocalModule {
    @Provides
    fun provideCacheDatabase(@ApplicationContext context: Context): CacheDatabase {
        return Room
            .databaseBuilder(
                context,
                CacheDatabase::class.java,
                "cache.db"
            )
            .build()
    }

    @Provides
    fun provideCacheDao(cacheDb: CacheDatabase): CacheDao {
        return cacheDb.cacheDao()
    }

    @Provides
    fun provideSearchCache(cacheDao: CacheDao): ISearchCache {
        return SearchCache(cacheDao)
    }
}
