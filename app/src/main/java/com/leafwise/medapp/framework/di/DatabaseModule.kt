package com.leafwise.medapp.framework.di

import android.content.Context
import androidx.room.Room
import com.leafwise.medapp.framework.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "medApp").build()

    @Provides
    @Singleton
    fun providesMedicationDao(database: AppDatabase) = database.medicationDao()

}