package com.ticketswap.cache

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface BaseCacheDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: T): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMany(data: List<T>): Completable

    @Delete
    fun delete(data: T): Single<Int>

    @Update
    fun set(data: T): Single<Int>
}
