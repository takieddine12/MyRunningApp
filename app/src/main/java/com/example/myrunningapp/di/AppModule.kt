package com.example.myrunningapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myrunningapp.HitlApplication_HiltComponents
import com.example.myrunningapp.db.RunDao
import com.example.myrunningapp.db.RunningDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext ctx : Context) = Room.databaseBuilder(ctx,RunningDB::class.java,
    "running.db").fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideDB(runningDB: RunningDB) = runningDB.runDao()
}