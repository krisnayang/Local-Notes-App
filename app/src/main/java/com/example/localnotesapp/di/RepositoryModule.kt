package com.example.localnotesapp.di

import com.example.localnotesapp.data.LocalDatabase
import com.example.localnotesapp.repository.ListNoteRepository
import com.example.localnotesapp.repository.ListNoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//dependency injection agar dapat mudah melakukan pengujian dan akses secara ganda pada app
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Provides
  @Singleton
  fun provideListNoteRepository(
    localDatabase: LocalDatabase
  ): ListNoteRepository {
    return ListNoteRepositoryImpl(localDatabase.dataDao)
  }
}