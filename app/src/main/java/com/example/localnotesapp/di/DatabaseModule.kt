package com.example.localnotesapp.di

import android.content.Context
import androidx.room.Room
import com.example.localnotesapp.data.LocalDatabase
import com.example.localnotesapp.data.dao.DataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//dependency injection agar dapat mudah melakukan pengujian dan akses secara ganda pada app
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun noteDao(localDatabase: LocalDatabase): DataDao {
    return localDatabase.dataDao
  }

  @Provides
  @Singleton
  fun provideNoteDatabase(@ApplicationContext context: Context): LocalDatabase {
    return Room.databaseBuilder(
      context,
      LocalDatabase::class.java,
      "local_db")
      .allowMainThreadQueries()
      .fallbackToDestructiveMigration()
      .build()
  }
}